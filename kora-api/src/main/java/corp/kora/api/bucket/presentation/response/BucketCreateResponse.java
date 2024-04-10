package corp.kora.api.bucket.presentation.response;

public record BucketCreateResponse(
	Long bucketId
) {

	public static BucketCreateResponse from(Long bucketId) {
		return new BucketCreateResponse(bucketId);
	}
}
