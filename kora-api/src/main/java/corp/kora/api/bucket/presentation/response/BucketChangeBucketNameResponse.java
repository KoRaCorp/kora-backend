package corp.kora.api.bucket.presentation.response;

public record BucketChangeBucketNameResponse(
	Long bucketId
) {

	public static BucketChangeBucketNameResponse from(Long bucketId) {
		return new BucketChangeBucketNameResponse(bucketId);
	}
}
