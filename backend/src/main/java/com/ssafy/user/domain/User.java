package com.ssafy.user.domain;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
public class User {
	private String userId;
	private String password;
	private String name;
	private String address;
	private Role role;
	
}
