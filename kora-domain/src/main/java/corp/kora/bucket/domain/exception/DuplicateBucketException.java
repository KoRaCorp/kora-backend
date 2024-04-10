package corp.kora.bucket.domain.exception;

import corp.kora.global.exception.BadRequestException;

public class DuplicateBucketException extends BadRequestException {
	public DuplicateBucketException(String message) {
		super(message);
	}
}
