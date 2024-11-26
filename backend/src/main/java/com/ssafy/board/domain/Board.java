package com.ssafy.board.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class Board {
	private int boardId;
	private String userId;
	private String subject;
	private String content;
	private Timestamp postDate;
	private int hitPoint;
}
