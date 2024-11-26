package com.ssafy.comment.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class CommentDto {
	
	@Setter
	@Getter
	@AllArgsConstructor
	public static class CommentRegistRequest{
		private int boardId;
		private String commentContent;
	}
}
