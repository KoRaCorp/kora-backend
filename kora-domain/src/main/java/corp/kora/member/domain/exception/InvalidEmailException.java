package corp.kora.member.domain.exception;

import corp.kora.global.exception.BadRequestException;

public class InvalidEmailException extends BadRequestException {
	public InvalidEmailException(String message) {
		super(message);
	}
}
