package corp.kora.global.exception;

public class NoAccessAuthenticationException extends RuntimeException {
	public NoAccessAuthenticationException(String message) {
		super(message);
	}
}
