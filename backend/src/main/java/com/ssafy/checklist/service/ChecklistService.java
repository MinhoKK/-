package com.ssafy.checklist.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.apt.service.dto.AptDto;
import com.ssafy.checklist.domain.Checklist;
import com.ssafy.checklist.domain.ChecklistApt;
import com.ssafy.checklist.domain.ChecklistAptItem;
import com.ssafy.checklist.domain.ChecklistItem;

public interface ChecklistService {
	// 체크리스트
	int registChecklist(String userId) throws Exception;
	Checklist findChecklist(String userId) throws Exception;
	
	// 체크리스트 항목
	List<ChecklistItem> findAllChecklistItem(int checklistId) throws Exception;
	int registChecklistItem(int checklistId, String item) throws Exception;
	void deleteChecklistItem(int itemId) throws Exception;
	
	// 내 체크리스트 조회
	List<AptDto> findMyChecklist(String userId) throws SQLException;
	
	// 아파트별 체크리스트
	int registChecklistApt(String aptId, String userId) throws Exception;
	ChecklistApt findChecklistApt(String aptId, String userId) throws SQLException;
	void deleteChecklistApt(int checklistAptId) throws SQLException;
	List<ChecklistAptItem> findAllChecklistAptItem(int checklistAptId) throws SQLException;
	void changeChecked(int itemId) throws Exception;
}
