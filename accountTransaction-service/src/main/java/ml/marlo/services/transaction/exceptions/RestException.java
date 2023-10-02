package ml.marlo.services.transaction.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException {

	private static final long serialVersionUID = -9106365546089010911L;
	private final HttpStatus httpStatus;
	private List<String> errorMessage = new ArrayList<>();

	public RestException(Object message, HttpStatus httpStatus) {
		super(message.toString());
		this.httpStatus = httpStatus;
		this.errorMessage.add(message.toString());
	}

	public RestException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
		this.errorMessage.add(message);
	}

	public RestException(List<String> message, HttpStatus httpStatus) {
		super(message.toString());
		this.httpStatus = httpStatus;
		this.errorMessage.addAll(message);
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public List<String> getErrorMessage() {
		return errorMessage;
	}

}
