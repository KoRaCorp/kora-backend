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
	public Optional<Member> findByNickname(String nickname) {
		if (nickname == null || nickname.isBlank()) {
			return Optional.empty();
		}

		return Optional.ofNullable(
			queryFactory
				.selectFrom(member)
				.where(nicknameEq(nickname))
				.fetchOne()
		);

	}

	@Override
	public Optional<Member> findLastNicknameSuffix(String nickname, String delimiter) {
		if (nickname == null || nickname.isBlank()) {
			return Optional.empty();
		}

		return Optional.ofNullable(
			queryFactory
				.selectFrom(member)
				.where(nicknameEq(nickname + delimiter))
				.orderBy(member.id.desc())
				.fetchFirst()
		);
	}

	@Override
	public Optional<Member> find(String authKey) {
		if (authKey == null || authKey.isBlank()) {
			return Optional.empty();
		}

		return Optional.ofNullable(
			queryFactory
				.selectFrom(member)
				.where(authKeyEq(authKey))
				.fetchOne()
		);
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

	private BooleanExpression authKeyEq(String authKey) {
		return authKey != null ? member.authKey.eq(authKey) : null;
	}

	private BooleanExpression nicknameEq(String nickname) {
		if (nickname == null || nickname.isBlank()) {
			return null;
		}
		return member.nickname.eq(nickname);
	}
}
