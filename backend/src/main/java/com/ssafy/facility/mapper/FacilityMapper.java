package com.ssafy.facility.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.facility.service.dto.FacilityDto;

@Mapper
public interface FacilityMapper {
	
	// 아파트의 좌표를 기준으로 편의시설을 검색함
	List<FacilityDto> getAllFacility(String point);
}
