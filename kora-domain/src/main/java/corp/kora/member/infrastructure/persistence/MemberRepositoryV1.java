package corp.kora.member.infrastructure.persistence;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static corp.kora.member.domain.model.QMember.member;

public class MemberRepositoryV1 implements MemberRepository {
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    public MemberRepositoryV1(EntityManager entityManager) {

        this.queryFactory = new JPAQueryFactory(entityManager);
        this.entityManager = entityManager;

    }

    @Override
    public Optional<Member> findById(Long memberId) {
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
    public Optional<Member> findByAuthKey(String authKey) {
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
    @Transactional
    public Member save(Member member) {
        if (isNew(member)) {
            entityManager.persist(member);
            return member;
        }
        return entityManager.merge(member);

    }

    @Override
    @Transactional
    public List<Member> saveAll(List<Member> members) {
        if (members.isEmpty()) {
            return List.of();
        }

        List<Member> result = new ArrayList<>(members.size());
        for (Member member : members) {
            result.add(save(member));
        }

        return result;
    }

    @Override
    @Transactional
    public void deleteAllInBatch() {
        entityManager.createQuery("delete from Member").executeUpdate();
    }

    private boolean isNew(Member member) {
        return member.getId() == null;
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
