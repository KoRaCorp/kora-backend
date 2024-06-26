package corp.kora.global.handler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import corp.kora.global.exception.BadRequestException;
import corp.kora.global.exception.NoAccessAuthenticationException;
import corp.kora.global.exception.NoAccessAuthorizationException;
import corp.kora.global.exception.NotFoundException;
import corp.kora.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
	private final HttpServletRequest httpServletRequest;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ApiResponse<Void> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		String message = bindingResult.getFieldErrors().get(0).getDefaultMessage();
		log.warn("message = {}", message);
		log.warn("e.getMessage() = {}", e.getMessage());
		return ApiResponse.badRequest(message);
	}

	@ExceptionHandler({BadRequestException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ApiResponse<Void> handleServiceException(BadRequestException e) {
		log.warn("e.getMessage() = {}", e.getMessage());
		return ApiResponse.badRequest(e.getMessage());
	}

	@ExceptionHandler({NotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	protected ApiResponse<Void> handleNotFoundException(NotFoundException e) {
		log.warn("e.getMessage() = {}", e.getMessage());
		return ApiResponse.notFound(e.getMessage());
	}

	@ExceptionHandler({NoAccessAuthorizationException.class})
	@ResponseStatus(HttpStatus.FORBIDDEN)
	protected ApiResponse<Void> handleNoAccessAuthorizationException(NoAccessAuthorizationException e) {
		log.warn("e.getMessage() = {}", e.getMessage());
		return ApiResponse.forbidden(e.getMessage());
	}

	@ExceptionHandler({NoAccessAuthenticationException.class})
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	protected ApiResponse<Void> handleNoAccessAuthenticationException(NoAccessAuthenticationException e) {
		log.warn("e.getMessage() = {}", e.getMessage());
		return ApiResponse.unauthorized(e.getMessage());
	}

	@ExceptionHandler({Exception.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected ApiResponse<Void> handleException(Exception e) {
		log.warn("e.getMessage() = {}", e.getMessage());
		return ApiResponse.from(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	}

}
