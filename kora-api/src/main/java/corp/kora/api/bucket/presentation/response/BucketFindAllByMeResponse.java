package corp.kora.api.bucket.presentation.response;

import corp.kora.bucket.domain.model.Bucket;

public record BucketFindAllByMeResponse(
	Long bucketId,
	String bucketName
) {
	public static BucketFindAllByMeResponse from(Bucket bucket) {
		return new BucketFindAllByMeResponse(bucket.getId(), bucket.getBucketName());
	}
}
