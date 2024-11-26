package com.ssafy.comment.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.comment.domain.Comment;

@Mapper
public interface CommentMapper {
	List<Comment> findAll(int boardId) throws SQLException;
	void regist(int boardId, String userId, String commentContent) throws SQLException;
	void delete(int commentId) throws SQLException;
}
