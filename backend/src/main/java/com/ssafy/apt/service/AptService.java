package com.ssafy.apt.service;

import java.util.List;

import com.ssafy.apt.service.dto.AptDealDto;
import com.ssafy.apt.service.dto.AptDto;
import com.ssafy.apt.service.dto.SearchDongDto;

public interface AptService {
	
	// 해당 동에 속하는 모든 아파트를 검색하는 기능
	public List<AptDto> getAllApartment(String dongCode) throws Exception;
	
	// 아파트 검색 기능
	public List<AptDto> searchApartmentByDongName(String dongName) throws Exception;
	
	// 아파트를 polygon을 기준으로 검색함
	public List<AptDto> searchApartmentByPolygon(String polygon) throws Exception;
	
	//아파트 일련번호를 기준으로 아파트 거래내역 가져옴
	public List<AptDealDto> searchApartmentDealByCode(String aptId) throws Exception;

	// 사용자가 입력하는 법정동 텍스트를 기반으로 검색 결과 가져옴
	public List<SearchDongDto> searchDong(String dongName) throws Exception;
}
