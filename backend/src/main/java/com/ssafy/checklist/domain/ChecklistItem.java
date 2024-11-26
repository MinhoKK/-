package com.ssafy.checklist.domain;

import lombok.Data;

@Data
public class ChecklistItem {
	private int itemId;
	private int checklistId;
	private String item;
	private boolean checked;
}
