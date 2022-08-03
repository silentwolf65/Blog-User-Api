package com.blogapi.user.services;

import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
	// upload/save image 
	String uploadImage(String path, MultipartFile file);
	
	// Download image
	InputStream getResource(String path, String fileName);
}
