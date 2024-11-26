package com.ssafy.token.service;

import org.springframework.stereotype.Service;

import com.ssafy.token.mapper.RefreshTokenMapper;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

	private RefreshTokenMapper refreshTokenMapper;
	
	
	public RefreshTokenServiceImpl(RefreshTokenMapper refreshTokenMapper) {
		super();
		this.refreshTokenMapper = refreshTokenMapper;
	}

	@Override
	public String findByUserId(String userId) throws Exception {
		return refreshTokenMapper.findByUserId(userId);
	}
	
	@Override
	public void saveRefreshToken(String userId, String token) throws Exception {
		refreshTokenMapper.saveRefreshToken(userId, token);
	}

	@Override
	public void deleteByUserId(String userId) throws Exception {
		refreshTokenMapper.deleteByUserId(userId);
	}

}
