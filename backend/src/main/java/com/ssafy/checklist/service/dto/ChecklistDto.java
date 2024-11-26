package com.ssafy.checklist.service.dto;

import java.util.List;

import com.ssafy.checklist.domain.ChecklistItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ChecklistDto {

	@Setter
	@Getter
	@AllArgsConstructor
	public static class ChecklistResponse{
		private int checklistId;
		private List<ChecklistItem> items;
	}
	
	@Setter
	@Getter
	@AllArgsConstructor
	public static class ChecklistItemRegist{
		private int checklistId;
		private String item;
	}
	
}
