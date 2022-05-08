package com.blogapi.user.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogapi.user.services.FileService;
@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) {
		//File name 
				String fileName = file.getOriginalFilename();
				
				//Random generate file name
				String randomId = UUID.randomUUID().toString();
				String fileName1 = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));
				
				//Full path
				String filePath = path+ File.separator+fileName1;
				
				//Create folder if not present
				File f = new File(path);
				if(!f.exists()) {
					f.mkdir();
				}
				
				//Copy file
				try {
					Files.copy(file.getInputStream(), Paths.get(filePath));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) {
		//can write db logic instead below code
		String fullPath = path+File.separator+fileName;
		try {
			return new FileInputStream(fullPath);
		} catch (FileNotFoundException e) {
			System.err.println("Error while fetching resource");
			e.printStackTrace();
			return null;
		}
	}
	
}
