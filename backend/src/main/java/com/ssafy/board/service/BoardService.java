package com.ssafy.board.service;

import java.util.List;
import com.ssafy.board.domain.Board;
import com.ssafy.board.service.dto.BoardDto;
import com.ssafy.board.service.dto.BoardDto.BoardResponse;

public interface BoardService {
	List<BoardDto.BoardListResponse> findAll(String filter, String userId) throws Exception;
	BoardDto.BoardResponse findById(int boardId) throws Exception;
	int updateHitPoint(int boardId) throws Exception;
	int regist(BoardDto.BoardRegistRequest boardDto) throws Exception;
	void modify(Board board) throws Exception;
	void delete(int boardId) throws Exception;
}
