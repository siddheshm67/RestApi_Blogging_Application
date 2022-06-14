package com.blog.serviceImpl;

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

import com.blog.service.FileService;
import com.fasterxml.jackson.core.sym.Name;

@Service
public class fileServiceImpl implements FileService {

	@Override
	public String uploadFile(String path, MultipartFile file) throws IOException {
		//filename
		String originalFilename = file.getOriginalFilename();
		
		//random name generator file
		String randomid = UUID.randomUUID().toString();
		String fileName1 = randomid.concat(originalFilename.substring(originalFilename.indexOf(".")));
		
		//fullPAth
		String filepathString = path+File.separator+fileName1;
		
		//create folder if not created
		File file2 = new File(path);
		if (!file2.exists()) {
			file2.mkdir();
		}
		
		//fileCopy
		Files.copy(file.getInputStream(), Paths.get(filepathString));
		return fileName1;
	}

	@Override
	public InputStream getresouce(String path, String fileNme) throws FileNotFoundException {
		String fullpathString = path+File.separator+fileNme;
		InputStream iStream = new FileInputStream(fullpathString);
		return iStream;
	}

}
