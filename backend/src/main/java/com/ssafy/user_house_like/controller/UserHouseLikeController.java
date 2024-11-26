package com.ssafy.user_house_like.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.apt.service.dto.AptDto;
import com.ssafy.user_house_like.service.UserHouseLikeService;
import com.ssafy.user_house_like.service.dto.UserHouseLikeDto;
import com.ssafy.util.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
@Slf4j
public class UserHouseLikeController {
	private final UserHouseLikeService userHouseLikeService;
	private final JWTUtil jwtUtil;
	
	@PostMapping("/apt")
	public ResponseEntity<?> addLike(@RequestBody UserHouseLikeDto userHouseLikeDto) throws Exception {
		
		String userId = userHouseLikeDto.getUserId();
		String aptId = userHouseLikeDto.getAptId();
		String like  = userHouseLikeDto.getLike();
		userHouseLikeService.checkLike(userId, aptId, like);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Success");
	}
	
	@GetMapping("/check")
	public ResponseEntity<?> likeAptList(HttpServletRequest request, @RequestParam(name = "aptId", required = true) String aptId) throws Exception {
		log.info("요청 확인");
		String userId = jwtUtil.getUserId(request.getHeader("Authorization"));
		boolean isLike = userHouseLikeService.isLike(userId, aptId) == 0 ? false : true;
		
		log.info("찜 확인 완료!");
		return ResponseEntity.status(HttpStatus.CREATED).body(isLike);
	}

}
