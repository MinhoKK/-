package com.ssafy.token.service;

public interface RefreshTokenService {
	String findByUserId(String userId) throws Exception;
	void saveRefreshToken(String userId, String token) throws Exception;
	void deleteByUserId(String userId) throws Exception;
}
