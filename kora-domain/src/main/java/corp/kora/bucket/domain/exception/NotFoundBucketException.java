package corp.kora.bucket.domain.exception;

import corp.kora.global.exception.NotFoundException;

public class NotFoundBucketException extends NotFoundException {
	public NotFoundBucketException(String message) {
		super(message);
	}
}
