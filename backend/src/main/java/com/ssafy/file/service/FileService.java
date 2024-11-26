package com.ssafy.file.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.file.mapper.FileMapper;
import com.ssafy.file.service.dto.FileDto.FileResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

    public static final String PROFILE_IMAGE = "profile";
    public static final String BOARD_IMAGE = "board";
    
    private final AmazonS3Client amazonS3Client;
    private final FileMapper fileMapper;

    @Transactional
    public FileResponse uploadFile(List<MultipartFile> multipartFiles, String filePath, int boardId) throws Exception {

        String uploadFilePath = filePath;
        List<String> uploadFileUrlList = new ArrayList<>();
        
        for (MultipartFile multipartFile : multipartFiles) {
        	String originalFileName = multipartFile.getOriginalFilename();
            String uploadFileName = getUuidFileName(originalFileName);
            String uploadFileUrl = "";

            ObjectMetadata objectMetadata = getObjectMetadata(multipartFile);
            uploadFileUrl = uploadToS3(multipartFile, uploadFilePath, uploadFileName, uploadFileUrl, objectMetadata);
            
            log.info(boardId + "번 게시글의 이미지 업로드 실시, url: " + uploadFileUrl + "등록");
            fileMapper.saveFile(boardId, uploadFileUrl);
            uploadFileUrlList.add(uploadFileUrl);
		}
        

        return new FileResponse(uploadFileUrlList);
    }

    private String uploadToS3(MultipartFile multipartFile, String uploadFilePath, String uploadFileName, String uploadFileUrl, ObjectMetadata objectMetadata) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            String keyName = uploadFilePath + "/" + uploadFileName;
            amazonS3Client.putObject(new PutObjectRequest(bucket, keyName, inputStream, objectMetadata));
            uploadFileUrl = amazonS3Client.getUrl(bucket, keyName).toString();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("S3로 파일 업로드가 실패했습니다.", e);
        }
        return uploadFileUrl;
    }
	
    private static ObjectMetadata getObjectMetadata(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        return objectMetadata;
    }
	
	private String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID().toString() + "." + ext;
    }
	
	
	public List<String> findFiles(int boardId) throws Exception{
		List<String> files = fileMapper.findFiles(boardId);
		return files;
	}
	
	public void deleteFiles(int boardId, String filePath) throws Exception{
		List<String> files = fileMapper.findFiles(boardId);
		deleteS3Files(files, filePath);
		
		
		fileMapper.deleteFiles(boardId);
	}
	
	private void deleteS3Files(List<String> files, String filePath) throws Exception{
		for (String fileUrl : files) {
			String uploadFileName = getFileNameFromURL(fileUrl);
	        String uploadFilePath = filePath;
	        
	        try {
	            String keyName = uploadFilePath + "/" + uploadFileName;
	            boolean isObjectExist = amazonS3Client.doesObjectExist(bucket, keyName);
	            if (isObjectExist) {
	                amazonS3Client.deleteObject(bucket, keyName);
	            } else {
	                log.info("file not found");
	            }
	        } catch (Exception e) {
	            log.info("Delete File failed", e);
	        }
		}
	}
	
	private String getFileNameFromURL(String url) {
        return url.substring(url.lastIndexOf('/') + 1, url.length());
    }
	
}
