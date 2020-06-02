package tech.icoding.springrest.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ApiDocController {
	@GetMapping("/doc/{name}")
	public ResponseEntity<Resource> apiGuide(@PathVariable String name) {
		Resource file = new ClassPathResource( "static/docs/"+name);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "text/html").body(file); 
	}
}
