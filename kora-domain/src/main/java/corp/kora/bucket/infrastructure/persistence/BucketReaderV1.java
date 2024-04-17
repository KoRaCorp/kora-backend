package corp.kora.bucket.infrastructure.persistence;

import static corp.kora.bucket.domain.model.QBucket.*;

import java.util.List;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import corp.kora.bucket.domain.model.BucketReadModel;
import corp.kora.bucket.domain.repository.BucketReader;
import jakarta.persistence.EntityManager;

public class BucketReaderV1 implements BucketReader {
	private final JPAQueryFactory queryFactory;

	public BucketReaderV1(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public List<BucketReadModel> readAllByMemberId(Long memberId) {
		if (memberId == null) {
			return List.of();
		}

		return queryFactory
			.select(bucketReadModel())
			.from(bucket)
			.where(memberIdEq(memberId))
			.fetch();

	}

	private ConstructorExpression<BucketReadModel> bucketReadModel() {
		return Projections.constructor(
			BucketReadModel.class,
			bucket.id,
			bucket.memberId,
			bucket.bucketName,
			bucket.createdAt,
			bucket.modifiedAt
		);
	}

	private BooleanExpression memberIdEq(Long memberId) {
		return memberId != null ? bucket.memberId.eq(memberId) : null;
	}
}
