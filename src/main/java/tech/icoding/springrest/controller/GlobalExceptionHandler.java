package tech.icoding.springrest.controller;

import java.util.NoSuchElementException;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;

import tech.icoding.springrest.data.MsgData;
import tech.icoding.springrest.service.storage.StorageFileNotFoundException;

//@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(value =NoSuchElementException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
    public MsgData notSuchElement(NoSuchElementException e){
    	LOG.info(e.getMessage());
		return new MsgData(e.getMessage());
    }
    
    @ExceptionHandler(value =MethodArgumentNotValidException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
    public MsgData methodArgumentNotValidException(MethodArgumentNotValidException e){
    	LOG.info(e.getMessage());
		return new MsgData(e.getMessage());
    } 
    @ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
    
    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<?> handleStorageFileNotFound(HttpStatusCodeException exc) {
		return ResponseEntity.status(exc.getStatusCode()).body(new MsgData(exc.getStatusText()));
	}
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> validationException(ValidationException exc) {
    	LOG.error("validation error", exc);
    	return ResponseEntity.badRequest().body(new MsgData(exc.getMessage()));
    }
    
}
