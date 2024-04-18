package corp.kora.bucket.infrastructure.persistence;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import corp.kora.bucket.domain.model.Bucket;
import corp.kora.bucket.domain.repository.BucketRepository;

public class FakeBucketRepository implements BucketRepository {

	private final Map<Long, Bucket> store = new ConcurrentHashMap<>();

	private final AtomicLong idGenerator = new AtomicLong();

	@Override
	public Optional<Bucket> findByMemberIdAndBucketName(Long memberId, String bucketName) {
		return Optional.empty();
	}

	@Override
	public Optional<Bucket> findById(Long bucketId) {
		return Optional.empty();
	}

	@Override
	public List<Bucket> findAllByMemberId(Long memberId) {
		return null;
	}

	@Override
	public Bucket save(Bucket bucket) {
		if (!bucket.isNew()) {
			return bucket;
		}

		long bucketId = idGenerator.incrementAndGet();
		setId(bucketId, bucket);
		store.put(bucketId, bucket);
		return bucket;

	}

	@Override
	public List<Bucket> saveAll(List<Bucket> buckets) {
		return null;
	}

	@Override
	public void deleteAllInBatch() {

	}

	@Override
	public void delete(Bucket bucket) {

	}

	private void setId(long bucketId, Bucket bucket) {
		try {
			Method method = Class.forName("org.springframework.test.util.ReflectionTestUtils")
				.getMethod("setField", Object.class, String.class, Object.class);

			method.invoke(null, bucket, "id", bucketId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
