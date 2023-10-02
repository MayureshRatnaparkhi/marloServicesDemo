package ml.marlo.services.account.exceptions;

import ml.marlo.services.account.response.APIResponse;
import ml.marlo.services.account.response.APIResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import java.util.Set;
import java.util.stream.Collectors;


@RestControllerAdvice
public class RestExceptionHandler {
	private final String DOUBLE_QUOTE_STRING = "\"";
	private final String BLANK_STRING = "";

	@ExceptionHandler(WebExchangeBindException.class)
	public ResponseEntity<APIResponse> handleWebExchangeBindException(
			WebExchangeBindException webExchangeBindException) {
		Set<String> errorSet = webExchangeBindException.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toSet());

		return new ResponseEntity<>(
				APIResponseMessage.builder().errors(errorSet)
						.statusCode(webExchangeBindException.getStatusCode().value()).build(),
				null, webExchangeBindException.getStatusCode());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<APIResponse> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException exception) {
		Set<String> errorSet = exception.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toSet());
		return new ResponseEntity<>(
				APIResponseMessage.builder().message(errorSet)
						.statusCode(HttpStatus.BAD_REQUEST.value()).build(),
				null, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RestException.class)
	public ResponseEntity<APIResponse> handleContentEdCustomException(RestException exception) {

		return new ResponseEntity<>(
				APIResponseMessage.builder().message(exception.getErrorMessage())
						.statusCode(exception.getHttpStatus().value()).build(),
				null, exception.getHttpStatus());
	}

	@ExceptionHandler(ServerWebInputException.class)
	public ResponseEntity<APIResponse> handleServerWebInputException(ServerWebInputException serverWebInputException) {

		return new ResponseEntity<>(
				APIResponseMessage.builder()
						.message(serverWebInputException.getMessage().replaceAll(DOUBLE_QUOTE_STRING, BLANK_STRING))
						.statusCode(serverWebInputException.getStatusCode().value()).build(),
				null, serverWebInputException.getStatusCode());
	}
}
