package com.ssafy.checklist.domain;

import lombok.Data;

@Data
public class ChecklistAptItem {
	private int itemId;
	private int checklistAptId;
	private String item;
	private boolean checked;
}
