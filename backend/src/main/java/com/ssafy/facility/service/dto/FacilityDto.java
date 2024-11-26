package com.ssafy.facility.service.dto;

import com.ssafy.facility.domain.Category;

import lombok.Data;

@Data
public class FacilityDto {
	private String facilityName;
	private Category category;
	private String roadAddress;
	private double lat;
	private double lon;
}
