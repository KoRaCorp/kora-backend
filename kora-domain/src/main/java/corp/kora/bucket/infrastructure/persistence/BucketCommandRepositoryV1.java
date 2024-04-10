package corp.kora.bucket.infrastructure.persistence;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import corp.kora.bucket.domain.model.Bucket;
import corp.kora.bucket.domain.repository.BucketCommandRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BucketCommandRepositoryV1 implements BucketCommandRepository {

	private final EntityManager entityManager;

	@Override
	@Transactional
	public Bucket save(Bucket bucket) {
		if (isNew(bucket)) {
			entityManager.persist(bucket);
			return bucket;
		}
		return entityManager.merge(bucket);

	}

	@Override
	@Transactional
	public List<Bucket> saveAll(List<Bucket> buckets) {
		return null;
	}

	@Override
	@Transactional
	public void deleteAllInBatch() {
		entityManager.createQuery("DELETE FROM Bucket").executeUpdate();
	}

	private boolean isNew(Bucket bucket) {
		return bucket.getId() == null;
	}
}
