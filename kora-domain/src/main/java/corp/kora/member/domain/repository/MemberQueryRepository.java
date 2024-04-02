package corp.kora.member.domain.repository;

import corp.kora.member.domain.model.Member;

import java.util.Optional;

public interface MemberQueryRepository {
    Optional<Member> find(Long memberId);
}
