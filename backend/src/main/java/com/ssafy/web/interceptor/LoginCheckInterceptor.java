package com.ssafy.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.util.JWTUtil;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

	private final JWTUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }
        try {
        	log.info("로그인 인터셉터 적용 완료");
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if(authorizationHeader == null) throw new JwtException("토큰이 없습니다.");
            String userid = jwtUtil.getUserId(authorizationHeader);
            jwtUtil.checkToken(userid);
        } catch (final RuntimeException e) {
        	log.debug("로그인 인터셉터 적용 -> 오류 발생!!!");
            response.sendRedirect("http://localhost:5173");
            return false;
        }
        return true;
	}
	
	
	
}
