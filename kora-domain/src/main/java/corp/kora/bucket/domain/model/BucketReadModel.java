package corp.kora.bucket.domain.model;

import java.time.LocalDateTime;

public record BucketReadModel(
	Long bucketId,
	String bucketName,
	Long memberId,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
}
