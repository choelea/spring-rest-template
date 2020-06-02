package tech.icoding.springrest.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/docs")
public class DocController extends FileController{
	
	@GetMapping("/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> get(@PathVariable String filename) {
		Resource file = findResource(filename);
		if(FilenameUtils.isExtension(file.getFilename(), "pdf")) {
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
					"inline; filename=\"" + file.getFilename() + "\"").header(HttpHeaders.CONTENT_TYPE, "application/pdf").body(file); 
		}else {
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=\"" + file.getFilename() + "\"").body(file);
		}
		
	}

	@PostMapping
	public Map<String,String> create(@RequestParam("file") MultipartFile file,
			HttpServletRequest request) {

		return super.handleFileUpload(file, request);
	}

	@Override
	protected String getPrefix() {
		return "docs";
	}

	@Override
	protected boolean keepOriginName() {
		return true;
	}

	

}
