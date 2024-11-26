package com.ssafy.comment.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.comment.domain.Comment;

public interface CommentService {
	List<Comment> findAll(int boardId) throws SQLException;
	void regist(int boardId, String userId, String commentContent) throws SQLException;
	void delete(int commentId) throws SQLException;
}
