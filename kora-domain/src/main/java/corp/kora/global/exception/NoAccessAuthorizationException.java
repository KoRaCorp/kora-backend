package corp.kora.global.exception;

public class NoAccessAuthorizationException extends RuntimeException {
	public NoAccessAuthorizationException(String message) {
		super(message);
	}
}
