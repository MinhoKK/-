package com.ssafy.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssafy.exception.custom.UnAuthorizedException;
import com.ssafy.token.service.RefreshTokenService;
import com.ssafy.user.domain.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTUtil {
	
	@Value("${jwt.salt}")
	private String salt;

	@Value("${jwt.access-token.expiretime}")
	private long accessTokenExpireTime;

	@Value("${jwt.refresh-token.expiretime}")
	private long refreshTokenExpireTime;
	
	private RefreshTokenService refreshTokenService;
	
	public JWTUtil(RefreshTokenService refreshTokenService) {
		super();
		this.refreshTokenService = refreshTokenService;
	}

	public String createAccessToken(String userId, Role role) {
		return create(userId, role, "access-token", accessTokenExpireTime);
	}

	public String createRefreshToken(String userId, Role role) {
		return create(userId, role, "refresh-token", refreshTokenExpireTime);
	}

	private String create(String userId, Role role, String subject, long expireTime) {

		Claims claims = Jwts.claims()
				.setSubject(subject)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expireTime));

		claims.put("userId", userId);
		claims.put("role", role);

		String jwt = Jwts.builder()
			.setHeaderParam("typ", "JWT").setClaims(claims)
			.signWith(SignatureAlgorithm.HS256, this.generateKey())
			.compact();

		return jwt;
	}

	private byte[] generateKey() {
		byte[] key = null;
		try {
			key = salt.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if (log.isInfoEnabled()) {
				e.printStackTrace();
			} else {
				log.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}
		return key;
	}
	

	public boolean checkToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(token);
			log.debug("claims: {}", claims);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	public String getUserId(String authorization) {
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(authorization);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new UnAuthorizedException();
		}
		Map<String, Object> value = claims.getBody();
		log.info("value : {}", value);
		return (String) value.get("userId");
	}
	
	// accessToken 재발급
	public String reissue(String refreshToken, String userId, Role role) throws Exception {
		log.debug("refreshToken : {}, userId : {}", refreshToken, userId);
		
		if(checkToken(refreshToken)) {
			if(refreshToken.equals(refreshTokenService.findByUserId(userId))) {
				// accessToken 재발급시 refreshToken도 함께 refreshToken 재발급도 고려해보기(이전에 했던 방법)
				String newAccessToken = createAccessToken(userId, role);
				log.debug("newAccessToken : {}", newAccessToken);
				log.debug("정상적으로 access token 재발급!!!");
				return newAccessToken;
			}
		}
		
		log.debug("여기 체크");
		return null;
	}
}
