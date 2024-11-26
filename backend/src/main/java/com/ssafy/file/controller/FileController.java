package com.ssafy.file.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.file.service.FileService;
import com.ssafy.file.service.dto.FileDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.ssafy.file.service.FileService.BOARD_IMAGE;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

	private final FileService fileService;
	
	/*
    @PostMapping("/board")
    public ResponseEntity<?> uploadProfileImage(@ModelAttribute FileDto.FileRequest fileRequest) {
    	log.info("파일 업로드 컨트롤러 진입");
        FileDto.FileResponse fileResponse = fileService.uploadFile(fileRequest.getFiles(), boardId, BOARD_IMAGE);
        return ResponseEntity.ok()
                .body(fileResponse);
    }
    */
    
}
