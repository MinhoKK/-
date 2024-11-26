package com.ssafy.checklist.domain;

import lombok.Data;

@Data
public class ChecklistApt {
	private int checklistAptId;
	private String aptId;
	private int checklistId;
	private String userId;
}
