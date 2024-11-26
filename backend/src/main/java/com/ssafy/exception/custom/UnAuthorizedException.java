package com.ssafy.exception.custom;

public class UnAuthorizedException extends RuntimeException{

	public UnAuthorizedException() {
		super("계정 권한이 유효하지 않습니다. 다시 로그인 하세요.");
	}
}
