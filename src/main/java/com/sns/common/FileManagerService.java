package com.sns.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManagerService {
	
	private Logger logger = LoggerFactory.getLogger(FileManagerService.class);
	
	public static final String FILE_UPLOAD_PATH = "D:\\godh22\\5_spring_project\\sns\\workspace\\images/";
	
//	public static final String FILE_UPLOAD_PATH = "C:\\Salt\\coading\\5_spring_project\\sns\\workspace\\images/";
			
	public String saveFile(String loginId, MultipartFile file) {
		
		String directoryName = loginId + "_" + System.currentTimeMillis();
		String filePath = FILE_UPLOAD_PATH + directoryName;
		
		File directory = new File(filePath);
		if (directory.mkdir() == false) {
			return null;
		}
		
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath + "/" + file.getOriginalFilename());
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return "/images/" + directoryName + "/" + file.getOriginalFilename();
	}
	
	public void deleteFile(String imagePath) {
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));
		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				logger.error("[이미지 삭제] 파일 삭제 실패. imagePath:{}", imagePath);
				return;
			}
		}
		
		path = path.getParent();
		if (Files.exists(path)) {
			try {
				Files.delete(path);
			} catch (IOException e) {
				logger.error("[이미지 삭제] 폴더 삭제 실패. imagePath:{}", imagePath);
				return;
			}
		}
	}
}
