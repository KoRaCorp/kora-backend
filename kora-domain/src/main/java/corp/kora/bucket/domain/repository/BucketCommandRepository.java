package corp.kora.bucket.domain.repository;

import java.util.List;

import corp.kora.bucket.domain.model.Bucket;

public interface BucketCommandRepository {

	Bucket save(Bucket bucket);

	List<Bucket> saveAll(List<Bucket> buckets);

	void deleteAllInBatch();
}
