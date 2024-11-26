package com.ssafy.file.service.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class FileDto {

	@Setter
	@Getter
	@AllArgsConstructor
	public static class FileRequest{
		private List<MultipartFile> files;
	}
	
	@Setter
	@Getter
	@AllArgsConstructor
	public static class FileResponse{
		private List<String> uploadFileUrls;
	}
	
}
