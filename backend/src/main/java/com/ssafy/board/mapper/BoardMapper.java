package com.ssafy.board.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.board.domain.Board;
import com.ssafy.board.service.dto.BoardDto;

@Mapper
public interface BoardMapper {
	List<Board> findAll(String filter, String userId) throws SQLException;
	Board findById(int boardId) throws SQLException;
	int updateHitPoint(int boardId) throws SQLException;
	int regist(BoardDto.BoardRegistRequest boardDto) throws SQLException;
	void modify(Board board) throws Exception;
	void delete(int boardId) throws SQLException;
}
