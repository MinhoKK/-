package com.ssafy.user.controller;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.apt.service.dto.AptDto;
import com.ssafy.user.domain.User;
import com.ssafy.user.service.UserService;
import com.ssafy.user.service.dto.UserDto;
import com.ssafy.util.JWTUtil;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.boot.web.server.Cookie.SameSite.NONE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	private final UserService userService;
	private final JWTUtil jwtUtil;
	
	public UserController(UserService userService, JWTUtil jwtUtil) {
		super();
		this.userService = userService;
		this.jwtUtil = jwtUtil;
	}
	
	// 회원가입
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody User user, Model model) throws Exception {
		if(userService.findByUserId(user.getUserId()) != null) {
			throw new DuplicateKeyException("존재하는 아이디 입니다.");
		}
		
		userService.join(user);
		return ResponseEntity.ok()
				.build();
	}

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user, HttpSession session, Model model) throws Exception {
		
		User loginUser = userService.login(user);
		
		String accessToken = jwtUtil.createAccessToken(loginUser.getUserId(), loginUser.getRole());
		String refreshToken = jwtUtil.createRefreshToken(loginUser.getUserId(), loginUser.getRole());
		log.debug("access token : {}", accessToken);
		log.debug("refresh token : {}", refreshToken);
			
		userService.saveRefreshToken(loginUser.getUserId(), refreshToken);
			
		ResponseCookie cookie =  createCookie(refreshToken);
			
		// Bearer 명시 고민해보기 -> DB에 Bearer와 함께 저장할지 or 클라이언트에게 전달할때만 Bearer를 명시할지
		return ResponseEntity.status(HttpStatus.CREATED)
				.header(AUTHORIZATION, accessToken)
				.header(SET_COOKIE, cookie.toString())
				.build();
	}
	
	// 회원 정보 조회
	@GetMapping("/info")
	public ResponseEntity<?> getInfo(HttpServletRequest request) throws Exception{
		String userId = jwtUtil.getUserId(request.getHeader("Authorization"));
		jwtUtil.checkToken(userId);
		log.info("사용 가능한 토큰입니다.");
		User user = userService.findByUserId(userId);
		UserDto.UserInfo userinfo = new UserDto.UserInfo(user.getUserId(), user.getName(), user.getRole());
		return ResponseEntity.ok(userinfo);
	}
	
	// 로그아웃
	@DeleteMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) throws Exception {
		String userId = jwtUtil.getUserId(request.getHeader("Authorization"));
		log.debug(userId + "의 로그아웃 실행");
		userService.deleteRefreshTokenByUserId(userId);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.build();
	}
	
	// Access Token 재발급
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@CookieValue("refreshToken") String refreshToken) throws Exception{
		log.info("Access Token 재발급 실행");
		String userId = jwtUtil.getUserId(refreshToken);
		User findUser = userService.findByUserId(userId);
		
		String newAccessToken = jwtUtil.reissue(refreshToken, userId, findUser.getRole());
		if(newAccessToken == null) {
			throw new JwtException("refreshToken이 유효하지 않습니다.");
		}
		
		return ResponseEntity.ok()
				.header(AUTHORIZATION, newAccessToken)
				.build();
	}
	
	// 마이페이지 조회
	@GetMapping("/mypage")
	public ResponseEntity<?> mypage(HttpServletRequest request) throws Exception {
		String userId = jwtUtil.getUserId(request.getHeader("Authorization"));
		User user = userService.findByUserId(userId);
		
		log.info(user.getName() + "의 마이페이지 조회");
		return ResponseEntity.ok(user);
	}
	
	// 내정보 수정
	@PostMapping("/modify")
	public String modify(UserDto.UserModify userDto, Model model, HttpSession session) {
		try {
			userService.modify(userDto);
			User updatedUser = userService.findByUserId(userDto.getUserId());
			session.setAttribute("userinfo", updatedUser);
			return "redirect:/user/mypage";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "/error/error";
		}
	}
	
	// 찜 아파트 조회
	@GetMapping("/likes")
	public ResponseEntity<?> likeAptList(HttpServletRequest request) throws Exception {
		String userId = jwtUtil.getUserId(request.getHeader("Authorization"));
		List<AptDto> likeAptList = userService.findLikeAptList(userId);
		log.info(likeAptList.toString());
		log.info("찜 리스트 조회");
		return ResponseEntity.ok(likeAptList);
	}
	
	private ResponseCookie createCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite(NONE.attributeValue())
                .maxAge(Duration.ofDays(30))	// refreshToken 만료 시간과 동일
                .build();
    }
	
}
