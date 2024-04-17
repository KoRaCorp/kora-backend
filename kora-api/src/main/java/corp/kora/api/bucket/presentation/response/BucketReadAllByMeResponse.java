package corp.kora.api.bucket.presentation.response;

import corp.kora.bucket.domain.model.BucketReadModel;

public record BucketReadAllByMeResponse(
	Long bucketId,
	String bucketName
) {
	public static BucketReadAllByMeResponse from(BucketReadModel bucketReadModel) {
		return new BucketReadAllByMeResponse(bucketReadModel.bucketId(), bucketReadModel.bucketName());
	}
}
