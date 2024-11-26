package com.ssafy.apt.service.dto;

import lombok.Data;

@Data
public class AptDto {    
	private String aptId; // 아파트 일련번호
	private String aptName; // 아파트 이름
	private int constructionYear; // 건축년도
	private String roadAddress; // 집주소
	private double summaryArea; // 아파트 대표 평수(최빈)
	private long summaryAmount; // 아파트 대표가(최빈 평수의 평균가)
	private double lat; // 위도
	private double lon; // 경도
}
