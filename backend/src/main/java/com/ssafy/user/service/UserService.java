package com.ssafy.user.service;

import java.util.List;
import java.util.Map;

import com.ssafy.apt.service.dto.AptDto;
import com.ssafy.user.domain.User;
import com.ssafy.user.service.dto.UserDto;

public interface UserService {
	void join(User user) throws Exception;
	User login(User user) throws Exception;
	void modify(UserDto.UserModify userDto) throws Exception;
	User findByUserId(String userId) throws Exception;
	List<AptDto> findLikeAptList(String userId) throws Exception;
	
	// ADMIN
	List<User> findAll(Map<String, String> map) throws Exception;
	void modifyByAdmin(User user) throws Exception;
	void delete(String userId) throws Exception;
	
	// UserService
	void saveRefreshToken(String userId, String token) throws Exception;
	void deleteRefreshTokenByUserId(String userId) throws Exception;
}
