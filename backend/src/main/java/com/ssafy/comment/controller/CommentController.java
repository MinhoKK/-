package com.ssafy.comment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.comment.domain.Comment;
import com.ssafy.comment.service.CommentService;
import com.ssafy.comment.service.dto.CommentDto;
import com.ssafy.util.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

	private final CommentService commentService;
	private final JWTUtil jwtUtil;
	
	@GetMapping("/{boardId}")
	public ResponseEntity<?> commentList(@PathVariable int boardId) throws Exception{
		log.info(boardId + "번 게시글 댓글 조회");
		List<Comment> comments = commentService.findAll(boardId);
		return ResponseEntity.ok(comments);
	}
	
	@PostMapping()
	public ResponseEntity<?> registComment(@RequestBody CommentDto.CommentRegistRequest commentRegistRequest
											, HttpServletRequest request) throws Exception{
		String userId = jwtUtil.getUserId(request.getHeader("Authorization"));
		commentService.regist(commentRegistRequest.getBoardId(), userId, commentRegistRequest.getCommentContent());
		return ResponseEntity.status(HttpStatus.CREATED)
				.build();
	}
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable int commentId) throws Exception{
		commentService.delete(commentId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.build();
	}
}
