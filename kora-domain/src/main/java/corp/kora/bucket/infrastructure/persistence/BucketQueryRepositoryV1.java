package corp.kora.bucket.infrastructure.persistence;

import static corp.kora.bucket.domain.model.QBucket.*;

import java.util.Optional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import corp.kora.bucket.domain.model.Bucket;
import corp.kora.bucket.domain.repository.BucketQueryRepository;
import corp.kora.member.domain.model.Member;
import jakarta.persistence.EntityManager;

public class BucketQueryRepositoryV1 implements BucketQueryRepository {

	private final JPAQueryFactory queryFactory;

	public BucketQueryRepositoryV1(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public Optional<Bucket> findByMemberAndBucketName(Member member, String bucketName) {
		if (member == null || bucketName == null || bucketName.isBlank()) {
			return Optional.empty();
		}

		return Optional.ofNullable(
			queryFactory.selectFrom(bucket)
				.where(
					memberEq(member),
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

	@Override
	public Optional<Bucket> findWithMemberById(Long bucketId) {
		if (bucketId == null) {
			return Optional.empty();
		}

		return Optional.ofNullable(
			queryFactory.selectFrom(bucket)
				.innerJoin(bucket.member).fetchJoin()
				.where(idEq(bucketId))
				.fetchOne()
		);
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

	private BooleanExpression memberEq(Member member) {
		return member != null ? bucket.member.eq(member) : null;
	}
}
