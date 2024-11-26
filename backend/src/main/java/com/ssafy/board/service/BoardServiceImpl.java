package com.ssafy.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.board.domain.Board;
import com.ssafy.board.mapper.BoardMapper;
import com.ssafy.board.service.dto.BoardDto;
import com.ssafy.board.service.dto.BoardDto.BoardResponse;
import com.ssafy.comment.domain.Comment;
import com.ssafy.comment.service.CommentService;
import com.ssafy.file.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.ssafy.file.service.FileService.BOARD_IMAGE;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {

	private final BoardMapper boardMapper;
	private final FileService fileService;
	private final CommentService commentService;
	
	@Override
	public List<BoardDto.BoardListResponse> findAll(String filter, String userId) throws Exception {
		List<Board> boards = boardMapper.findAll(filter, userId);
		List<BoardDto.BoardListResponse> boardListResponses = new ArrayList<>();
		
		for (Board board : boards) {
			List<String> files = fileService.findFiles(board.getBoardId());
			List<Comment> comments = commentService.findAll(board.getBoardId());
			log.info("댓글 수: " + comments.size());
			
			BoardDto.BoardListResponse boardListResponse = new BoardDto.BoardListResponse(board, files, comments.size());
			boardListResponses.add(boardListResponse);
	    }
		
		return boardListResponses;
	}
	
	@Override
	public BoardDto.BoardResponse findById(int boardId) throws Exception {
		Board board = boardMapper.findById(boardId);
		List<String> files = fileService.findFiles(board.getBoardId());
		BoardResponse boardResponse = new BoardDto.BoardResponse(board, files);
		
		return boardResponse;
	}

	@Override
	@Transactional
	public int updateHitPoint(int boardId) throws Exception {
		return boardMapper.updateHitPoint(boardId);
	}

	@Override
	@Transactional
	public int regist(BoardDto.BoardRegistRequest boardDto) throws Exception {
		return boardMapper.regist(boardDto);
	}

	@Override
	@Transactional
	public void modify(Board board) throws Exception {
		boardMapper.modify(board);
	}
	
	@Override
	@Transactional
	public void delete(int boardId) throws Exception {
		boardMapper.delete(boardId);
		fileService.deleteFiles(boardId, BOARD_IMAGE);
	}

}
