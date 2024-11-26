package com.ssafy.user.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.apt.service.dto.AptDto;
import com.ssafy.user.domain.User;
import com.ssafy.user.service.dto.UserDto;

@Mapper
public interface UserMapper {
	void join(User user) throws SQLException;
	User login(User user) throws SQLException;
	void modify(UserDto.UserModify userDto) throws SQLException;
	User findByUserId(String userId) throws SQLException;
	List<AptDto> findLikeAptList(String userId) throws SQLException;
	
	// ADMIN
	List<User> findAll(Map<String, String> map) throws Exception;
	void modifyByAdmin(User user) throws Exception;
	void delete(String userId) throws Exception;
}
