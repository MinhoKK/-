package com.ssafy.file.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.file.service.dto.FileDto.FileResponse;

@Mapper
public interface FileMapper {
	void saveFile(int boardId, String fileUrl) throws SQLException;
	List<String> findFiles(int boardId) throws SQLException;
	public void deleteFiles(int boardId) throws SQLException;
}
