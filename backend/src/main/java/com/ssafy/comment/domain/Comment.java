package com.ssafy.comment.domain;

import lombok.Data;

@Data
public class Comment {
	private int commentId;
	private int boardId;
	private String userId;
	private String commentContent;
	private String commentCreateDate;
}
