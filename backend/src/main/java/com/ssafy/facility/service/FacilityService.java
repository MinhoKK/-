package com.ssafy.facility.service;

import java.util.List;

import com.ssafy.facility.service.dto.FacilityDto;

public interface FacilityService {
	
	// 아파트의 좌표를 기준으로 편의시설을 검색함
	public List<FacilityDto> getAllFacility(String Point) throws Exception;
}
