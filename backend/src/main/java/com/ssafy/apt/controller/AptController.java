package com.ssafy.apt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.apt.service.AptService;
import com.ssafy.apt.service.dto.AptDealDto;
import com.ssafy.apt.service.dto.AptDto;
import com.ssafy.apt.service.dto.SearchDongDto;
import com.ssafy.util.MapUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/house")
@RequiredArgsConstructor
public class AptController {
    
    private final AptService aptService;

    @GetMapping("/search")
    public ResponseEntity<List<AptDto>> getApartments(@RequestParam(name = "dongCode", required = true) String dongCode) throws Exception {
        List<AptDto> apartments = aptService.getAllApartment(dongCode);
        return ResponseEntity.ok(apartments);
    }
    
    @PostMapping("/search-polygon")
    public ResponseEntity<List<AptDto>> searchApartmentsByPolygon(@RequestBody List<Map<String, Double>> polygon) throws Exception {
    	// polygon 문자열로 변환
    	String polygonString = MapUtil.convertToPolygonString(polygon);
        List<AptDto> apartments = aptService.searchApartmentByPolygon(polygonString);
        return ResponseEntity.ok(apartments);
    }
    
    @GetMapping("/detail/{aptId}")
    public ResponseEntity<List<AptDealDto>> searchApartmentDealByCode(@PathVariable("aptId") String aptId) throws Exception {
    	List<AptDealDto> apartmentDetails = aptService.searchApartmentDealByCode(aptId);
        return ResponseEntity.ok(apartmentDetails);
    }

    @GetMapping("/search-dong")
    public ResponseEntity<List<SearchDongDto>> searchDong(@RequestParam(name = "query", required = true) String query) throws Exception {
        List<SearchDongDto> dongs = aptService.searchDong(query);
        return ResponseEntity.ok(dongs);
    }
    
    
}
