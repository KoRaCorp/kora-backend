package corp.kora.member.domain.repository;

import corp.kora.member.domain.model.Member;

import java.util.List;

public interface MemberCommandRepository {
    Member save(Member member);

    List<Member> saveAll(List<Member> members);

    void deleteAllInBatch();
}
