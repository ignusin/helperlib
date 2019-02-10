package ig.helperlib.web.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorDTO {
	private final HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private final LocalDateTime timestamp;
	private final String message;

	public ErrorDTO(HttpStatus status, Throwable ex) {
		timestamp = LocalDateTime.now();
	    this.status = status;
	    this.message = ex.getMessage();
	}
		
	public HttpStatus getStatus() {
		return status;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public String getMessage() {
		return message;
	}
}
