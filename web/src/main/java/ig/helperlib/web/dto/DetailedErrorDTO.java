package ig.helperlib.web.dto;

import org.springframework.http.HttpStatus;

import ig.helperlib.utils.Exceptions;

public class DetailedErrorDTO extends ErrorDTO {
	private final String stackTrace;
	
	public DetailedErrorDTO(HttpStatus status, Throwable ex) {
		super(status, ex);
		this.stackTrace = Exceptions.stackTraceAsString(ex);
	}
	
	public String getStackTrace() {
		return stackTrace;
	}
}
