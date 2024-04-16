package corp.kora.member.infrastructure.persistence;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import corp.kora.member.domain.model.MemberReadModel;
import corp.kora.member.domain.repository.MemberReader;
import jakarta.persistence.EntityManager;

import java.util.Optional;

import static corp.kora.member.domain.model.QMember.member;

public class MemberReaderV1 implements MemberReader {
    private final JPAQueryFactory queryFactory;

    public MemberReaderV1(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public Optional<MemberReadModel> readById(Long memberId) {
        if (memberId == null) {
            return Optional.empty();
        }


        return Optional.ofNullable(
                queryFactory.select(getConstructorExpression())
                        .from(member)
                        .where(member.id.eq(memberId))
                        .fetchOne()
        );
    }

    private ConstructorExpression<MemberReadModel> getConstructorExpression() {
        return Projections.constructor(MemberReadModel.class, member.id, member.nickname, member.email, member.profileMessage, member.profileImageFilePath);
    }
}
