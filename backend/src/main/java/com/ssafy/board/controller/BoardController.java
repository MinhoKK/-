package com.ssafy.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.board.domain.Board;
import com.ssafy.board.service.BoardService;
import com.ssafy.board.service.dto.BoardDto;
import com.ssafy.file.service.FileService;
import com.ssafy.file.service.dto.FileDto;
import com.ssafy.user.domain.User;
import com.ssafy.user.service.UserService;
import com.ssafy.util.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.ssafy.file.service.FileService.BOARD_IMAGE;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {

	private final BoardService boardService;
	private final UserService userService;
	private final FileService fileService;
	private final JWTUtil jwtUtil;
	
	@GetMapping()
	public ResponseEntity<?> boardList(@RequestParam String filter, HttpServletRequest request) throws Exception {
		String userId = jwtUtil.getUserId(request.getHeader("Authorization"));
		
		log.info(userId + "님의 " + filter + " 게시글 조회");
		
		List<BoardDto.BoardListResponse> boards = boardService.findAll(filter, userId);
		return ResponseEntity.ok(boards);
	}
	
	@GetMapping("/{boardId}")
	public ResponseEntity<?> boardView(@PathVariable int boardId) throws Exception {
		boardService.updateHitPoint(boardId);
		BoardDto.BoardResponse boardResponse = boardService.findById(boardId);
		
		return ResponseEntity.ok(boardResponse);
	}
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> registBoard(
	        @RequestPart(value = "files", required = false) List<MultipartFile> files,
	        @RequestPart("boardRegist") BoardDto.BoardRegistRequest boardRegist) throws Exception {
		try {
			boardService.regist(boardRegist);
		} catch (Exception e) {
			throw new RuntimeException("제목 또는 본문을 작성해주세요.");
		}
	    
	    int boardId = boardRegist.getBoardId();
	    
	    if (files != null && !files.isEmpty()) {
	        FileDto.FileResponse fileResponse = fileService.uploadFile(files, BOARD_IMAGE, boardId);
	    }
	    
	    return ResponseEntity.status(HttpStatus.CREATED).body(new BoardDto.BoardRegistResponse(boardId));
	}
	
	@PutMapping()
	public ResponseEntity<?> modifyBoard(@RequestBody Board board) throws Exception {
		log.info(board.getBoardId() + "의 게시판 수정 실행");
		log.info(board.toString());
		boardService.modify(board);
		return ResponseEntity.ok()
				.body(board.getBoardId());
	}
	
	
	
	@DeleteMapping("/{boardId}")
	public ResponseEntity<?> deleteBoard(@PathVariable int boardId) throws Exception {
		boardService.delete(boardId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT)
				.build();
	}
}
