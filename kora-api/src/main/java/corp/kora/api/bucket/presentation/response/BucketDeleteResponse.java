package corp.kora.api.bucket.presentation.response;

public record BucketDeleteResponse(
	Long bucketId
) {

	public static BucketDeleteResponse from(Long bucketId) {
		return new BucketDeleteResponse(bucketId);
	}
}
