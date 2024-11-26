package com.ssafy.apt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.apt.mapper.AptMapper;
import com.ssafy.apt.service.dto.AptDealDto;
import com.ssafy.apt.service.dto.AptDto;
import com.ssafy.apt.service.dto.SearchDongDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AptServiceImpl implements AptService {
	
	private final AptMapper aptMapper;

	@Override
	public List<AptDto> getAllApartment(String dongCode) throws Exception {
		return aptMapper.getAllApartment(dongCode);
	}

	@Override
	public List<AptDto> searchApartmentByDongName(String dongName) throws Exception {
		return aptMapper.searchApartmentByDongName(dongName);
	}

	@Override
	public List<AptDto> searchApartmentByPolygon(String polygon) throws Exception {
		return aptMapper.searchApartmentByPolygon(polygon);
	}

	@Override
	public List<AptDealDto> searchApartmentDealByCode(String aptId) throws Exception {
		return aptMapper.searchApartmentDealByCode(aptId);
	}

	@Override
	public List<SearchDongDto> searchDong(String dongName) throws Exception {
		return aptMapper.searchDong(dongName);
	}
	
	

}
