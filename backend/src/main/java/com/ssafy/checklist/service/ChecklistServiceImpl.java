package com.ssafy.checklist.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.apt.service.dto.AptDto;
import com.ssafy.checklist.domain.Checklist;
import com.ssafy.checklist.domain.ChecklistApt;
import com.ssafy.checklist.domain.ChecklistAptItem;
import com.ssafy.checklist.domain.ChecklistItem;
import com.ssafy.checklist.mapper.ChecklistMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChecklistServiceImpl implements ChecklistService {

	private final ChecklistMapper checklistMapper;
	
	@Override
	public int registChecklist(String userId) throws Exception {
		return checklistMapper.registChecklist(userId);
	}
	
	@Override
	public Checklist findChecklist(String userId) throws Exception {
		return checklistMapper.findChecklist(userId);
	}

	@Override
	public List<ChecklistItem> findAllChecklistItem(int checklistId) throws Exception {
		List<ChecklistItem> items = checklistMapper.findAllChecklistItem(checklistId);
		return items;
	}
	
	@Override
	public int registChecklistItem(int checklistId, String item) throws Exception {
		return checklistMapper.registChecklistItem(checklistId, item);
	}

	@Override
	public void deleteChecklistItem(int itemId) throws Exception {
		checklistMapper.deleteChecklistItem(itemId);
	}
	
	@Override
	public List<AptDto> findMyChecklist(String userId) throws SQLException {
		return checklistMapper.findMyChecklist(userId);
	}

	// 아파트별 체크리스트 적용(생성)
	@Transactional
	@Override
	public int registChecklistApt(String aptId, String userId) throws Exception {
		Checklist checklist = checklistMapper.findChecklist(userId);
		
		log.info("아파트번호: " + aptId + ", 체크리스트번호: " + checklist.getChecklistId() + ", 유저번호: " + userId);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("checklistAptId", null);
	    paramMap.put("aptId", aptId);
	    paramMap.put("checklistId", checklist.getChecklistId());
	    paramMap.put("userId", userId);
	    
		checklistMapper.registChecklistApt(paramMap);
		
		int checklistAptId = Integer.parseInt(paramMap.get("checklistAptId").toString());
		
		// 해당 유저의 체크리스트 항목 가져오기
		List<ChecklistItem> items = checklistMapper.findAllChecklistItem(checklist.getChecklistId());
		
		// 아파트의 체크리스트 항목에 추가하기
		for (ChecklistItem item : items) {
			checklistMapper.registChecklistAptItem(checklistAptId, item.getItem());
		}
		
		return checklistAptId;
	}
	
	@Override
	public ChecklistApt findChecklistApt(String aptId, String userId) throws SQLException {
		return checklistMapper.findChecklistApt(aptId, userId);
	}

	@Override
	public List<ChecklistAptItem> findAllChecklistAptItem(int checklistAptId) throws SQLException {
		List<ChecklistAptItem> items = checklistMapper.findAllChecklistAptItem(checklistAptId);
		return items;
	}


	@Override
	public void changeChecked(int itemId) throws Exception {
		checklistMapper.changeChecked(itemId);
	}

	@Override
	public void deleteChecklistApt(int checklistAptId) throws SQLException {
		checklistMapper.deleteChecklistApt(checklistAptId);
	}
}
