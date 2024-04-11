package corp.kora.bucket.infrastructure.persistence;

import static corp.kora.bucket.domain.model.QBucket.*;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import corp.kora.bucket.domain.model.Bucket;
import corp.kora.bucket.domain.repository.BucketRepository;
import jakarta.persistence.EntityManager;

public class BucketRepositoryV1 implements BucketRepository {

	private final JPAQueryFactory queryFactory;
	private final EntityManager entityManager;

	public BucketRepositoryV1(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
		this.entityManager = entityManager;
	}

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

	@Override
	@Transactional
	public void delete(Bucket bucket) {
		entityManager.remove(bucket);
	}

	@Override
	public List<Bucket> findAllByMemberId(Long memberId) {
		if (memberId == null) {
			return List.of();
		}

		return queryFactory
			.selectFrom(bucket)
			.where(memberIdEq(memberId))
			.fetch();
	}

	@Override
	public Optional<Bucket> findByMemberIdAndBucketName(Long memberId, String bucketName) {
		if (memberId == null || bucketName == null || bucketName.isBlank()) {
			return Optional.empty();
		}

		return Optional.ofNullable(
			queryFactory.selectFrom(bucket)
				.where(
					memberIdEq(memberId),
					bucketNameEq(bucketName)
				)
				.fetchOne());
	}

	@Override
	public Optional<Bucket> findById(Long bucketId) {
		if (bucketId == null) {
			return Optional.empty();
		}

		return Optional.ofNullable(queryFactory.selectFrom(bucket)
			.where(idEq(bucketId))
			.fetchOne());
	}

	private BooleanExpression idEq(Long bucketId) {
		return bucketId != null ? bucket.id.eq(bucketId) : null;
	}

	private BooleanExpression bucketNameEq(String bucketName) {
		if (bucketName == null || bucketName.isBlank()) {
			return null;
		}
		return bucket.bucketName.eq(bucketName);
	}

	private boolean isNew(Bucket bucket) {
		return bucket.getId() == null;
	}

	private BooleanExpression memberIdEq(Long memberId) {
		return memberId != null ? bucket.memberId.eq(memberId) : null;
	}
}
