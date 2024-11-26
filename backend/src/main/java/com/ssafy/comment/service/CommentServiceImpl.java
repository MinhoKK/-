package com.ssafy.comment.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.comment.domain.Comment;
import com.ssafy.comment.mapper.CommentMapper;
import com.ssafy.util.JWTUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentMapper commentMapper;
	
	@Override
	public List<Comment> findAll(int boardId) throws SQLException {
		List<Comment> comments = commentMapper.findAll(boardId);
		return comments;
	}

	@Override
	public void regist(int boardId, String userId, String commentContent) throws SQLException {
		commentMapper.regist(boardId, userId, commentContent);
	}

	@Override
	public void delete(int commentId) throws SQLException {
		commentMapper.delete(commentId);
	}

}
