package com.ssafy.apt.service.dto;

import lombok.Data;
@Data
public class SearchDongDto {
	private String legalDongFullCode; // 법정동 읍면동까지 기준 10자리 코드
	private String legalAddress; // 법정동주소
	
}
