package corp.kora.bucket.domain.repository;

import java.util.List;
import java.util.Optional;

import corp.kora.bucket.domain.model.Bucket;

public interface BucketRepository {
	Optional<Bucket> findByMemberIdAndBucketName(Long memberId, String bucketName);

	Optional<Bucket> findById(Long bucketId);

	List<Bucket> findAllByMemberId(Long memberId);

	Bucket save(Bucket bucket);

	List<Bucket> saveAll(List<Bucket> buckets);

	void deleteAllInBatch();

	void delete(Bucket bucket);
}
