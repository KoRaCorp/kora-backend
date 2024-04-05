package corp.kora.member.domain.exception;

import corp.kora.global.exception.NotFoundException;

public class NotFoundMemberException extends NotFoundException {
	public NotFoundMemberException(String message) {
		super(message);
	}
}
