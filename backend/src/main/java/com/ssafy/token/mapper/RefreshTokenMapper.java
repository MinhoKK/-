package com.ssafy.token.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenMapper {
	String findByUserId(String userId) throws Exception;
	void saveRefreshToken(String userId, String token) throws SQLException;
	void deleteByUserId(String userId) throws SQLException;
}
