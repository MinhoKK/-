package com.ssafy.checklist.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.apt.service.dto.AptDto;
import com.ssafy.checklist.domain.Checklist;
import com.ssafy.checklist.domain.ChecklistApt;
import com.ssafy.checklist.domain.ChecklistAptItem;
import com.ssafy.checklist.domain.ChecklistItem;

@Mapper
public interface ChecklistMapper {
	// 체크리스트
	int registChecklist(String userId) throws SQLException;
	Checklist findChecklist(String userId) throws SQLException;
	
	// 체크리스트 항목
	List<ChecklistItem> findAllChecklistItem(int checklistId) throws SQLException;
	int registChecklistItem(int checklistId, String item) throws SQLException;
	void deleteChecklistItem(int itemId) throws SQLException;
	
	// 내 체크리스트 조회
	List<AptDto> findMyChecklist(String userId) throws SQLException;
	
	// 아파트별 체크리스트
	int registChecklistApt(Map<String, Object> paramMap) throws SQLException;
	ChecklistApt findChecklistApt(String aptId, String userId) throws SQLException;
	void registChecklistAptItem(int checklistAptId, String item) throws SQLException;
	void deleteChecklistApt(int checklistAptId) throws SQLException;
	List<ChecklistAptItem> findAllChecklistAptItem(int checklistAptId) throws SQLException;
	void changeChecked(int itemId) throws SQLException;
}
