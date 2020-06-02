package tech.icoding.springrest.controller;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import tech.icoding.springrest.service.storage.StorageService;

abstract class FileController {

	@Autowired
	protected StorageService storageService;
	
	@Value("${nginx.static.path}")
	private String nginxPath;

	public Resource findResource(String filename) {

		return storageService.loadAsResource(getPrefix()+"/"+filename);
		
	}

	
	public Map<String,String> handleFileUpload(MultipartFile file,
			HttpServletRequest request) {

		String relativePath = storageService.store(file, getPrefix(),keepOriginName());
		
		return Collections.singletonMap("url", nginxPath+"/"+relativePath);
	}
	
	abstract protected String getPrefix();
	protected boolean keepOriginName() {
		return false;
	}

}
