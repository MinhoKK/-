package com.ssafy.user.service.dto;

import com.ssafy.user.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class UserDto {

	@Setter
	@Getter
	@AllArgsConstructor
	public static class UserModify{
		private String userId;
		private String address;
		private String password;
		private String passwordCheck;
	}
	
	@Setter
	@Getter
	@AllArgsConstructor
	public static class UserInfo{
		private String userId;
		private String name;
		private Role role;
	}
	
}
