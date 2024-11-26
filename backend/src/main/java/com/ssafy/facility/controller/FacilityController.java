package com.ssafy.facility.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.facility.service.FacilityService;
import com.ssafy.facility.service.dto.FacilityDto;
import com.ssafy.util.MapUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/facility")
@RequiredArgsConstructor
public class FacilityController {
	
	private final FacilityService facilityService;
	
	@GetMapping("/search")
    public ResponseEntity<List<FacilityDto>> getApartments(
    		@RequestParam(name = "lat", required = true) double lat,
    		@RequestParam(name = "lon", required = true) double lon) throws Exception {
		// Point 문자열로 변환
		String pointString = MapUtil.convertToPointString(lat, lon);
		System.out.println("변환성공");
        List<FacilityDto> apartments = facilityService.getAllFacility(pointString);
        return ResponseEntity.ok(apartments);
    }

}
