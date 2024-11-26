package com.ssafy.token.domain;

import java.time.LocalDateTime;

import io.jsonwebtoken.JwtException;
import lombok.Getter;

@Getter
public class RefreshToken {
	private String userId;
	private String token;
	private LocalDateTime expirationTime;
	
	public RefreshToken(String userId, String token, LocalDateTime expirationTime) {
        this.userId = userId;
        this.token = token;
        this.expirationTime = expirationTime;
    }

    public void switchRefreshToken(final String newToken) {
        validateNewToken(newToken);
        token = newToken;
    }

    private void validateNewToken(final String newToken) {
        if (newToken == null || newToken.isBlank()) {
            throw new IllegalArgumentException("refresh token 이 null 이나 공백으로 입력되었습니다.");
        }

        if (newToken.equals(token)) {
            throw new IllegalArgumentException("생성된 토큰이 기존 토큰과 같습니다.");
        }
    }

    public void validateExpirationTime(final LocalDateTime now) {
        if (now.isAfter(expirationTime)) {
            throw new JwtException("Refresh 토큰이 만료되었습니다.");
        }
    }
}
