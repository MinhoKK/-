package com.ssafy.board.service.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.board.domain.Board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class BoardDto {
	
	@Setter
	@Getter
	@AllArgsConstructor
	public static class BoardRegistRequest{
		private int boardId;
		private String userId;
		private String subject;
		private String content;
	}
	
	@Setter
	@Getter
	@AllArgsConstructor
	public static class BoardRegistResponse{
		private int boardId;
	}
	
	@Setter
	@Getter
	@AllArgsConstructor
	public static class BoardResponse{
		private Board board;
		private List<String> files;
	}
	
	@Setter
	@Getter
	@AllArgsConstructor
	public static class BoardListResponse{
		private Board board;
		private List<String> files;
		private int commentCount;
	}
	

}
