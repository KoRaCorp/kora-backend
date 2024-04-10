package corp.kora.api.bucket.presentation.request;

import corp.kora.api.bucket.application.BucketCreateProcessor;

public record BucketCreateRequest(
	String bucketName
) {

	public BucketCreateProcessor.Command toCommand(Long loginMemberId) {
		return new BucketCreateProcessor.Command(loginMemberId, bucketName);
	}
}
