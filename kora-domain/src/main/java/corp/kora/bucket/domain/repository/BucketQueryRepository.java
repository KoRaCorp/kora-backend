package corp.kora.bucket.domain.repository;

import java.util.Optional;

import corp.kora.bucket.domain.model.Bucket;
import corp.kora.member.domain.model.Member;

public interface BucketQueryRepository {
	Optional<Bucket> findByMemberAndBucketName(Member member, String bucketName);

	Optional<Bucket> findById(Long bucketId);

	Optional<Bucket> findWithMemberById(Long bucketId);
}
