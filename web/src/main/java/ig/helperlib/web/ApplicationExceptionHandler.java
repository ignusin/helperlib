package ig.helperlib.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ig.helperlib.web.dto.DetailedErrorDTO;
import ig.helperlib.web.dto.ErrorDTO;

@ControllerAdvice
public class ApplicationExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);
	
	@Autowired
	private Environment env;
	
	@ExceptionHandler()
	public ResponseEntity<Object> handleExceptionInternal(Exception ex) {
		logger.error("Unexpected runtime error", ex);
		
		if (ex instanceof MethodArgumentNotValidException) {
			return buildResponseEntity(HttpStatus.BAD_REQUEST, ex);
		}
		
		return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}
	
	private ResponseEntity<Object> buildResponseEntity(HttpStatus status, Throwable ex) {	
		Boolean devMode = env.getProperty("application.developmentMode", Boolean.class);
		
		if (devMode != null && devMode == true) {
			return new ResponseEntity<>(new DetailedErrorDTO(status, ex), status);
		}
		else {
			return new ResponseEntity<>(new ErrorDTO(status, ex), status);
		}
	}
}
