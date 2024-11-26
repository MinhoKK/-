package com.ssafy.facility.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.facility.mapper.FacilityMapper;
import com.ssafy.facility.service.dto.FacilityDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {
	
	private final FacilityMapper facilityMapper;

	@Override
	public List<FacilityDto> getAllFacility(String Point) throws Exception {
		return facilityMapper.getAllFacility(Point);
	}
	
	

}
