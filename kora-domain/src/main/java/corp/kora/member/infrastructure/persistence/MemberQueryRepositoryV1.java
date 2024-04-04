package corp.kora.member.infrastructure.persistence;

import static corp.kora.member.domain.model.QMember.*;

import java.util.Optional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.repository.MemberQueryRepository;
import jakarta.persistence.EntityManager;

public class MemberQueryRepositoryV1 implements MemberQueryRepository {
	private final JPAQueryFactory queryFactory;

	public MemberQueryRepositoryV1(EntityManager entityManager) {
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public Optional<Member> find(String authKey) {
		return Optional.empty();
	}

	@Override
	public Optional<Member> find(Long memberId) {
		if (memberId == null) {
			return Optional.empty();
		}

		return Optional.ofNullable(
			queryFactory
				.selectFrom(member)
				.where(memberIdEq(memberId))
				.fetchOne()
		);
	}

	private BooleanExpression memberIdEq(Long memberId) {
		return memberId != null ? member.id.eq(memberId) : null;
	}
}
