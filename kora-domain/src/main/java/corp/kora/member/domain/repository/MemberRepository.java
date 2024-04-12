package corp.kora.member.domain.repository;

import corp.kora.member.domain.model.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findByAuthKey(String authKey);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findLastNicknameSuffix(String nickname, String delimiter);

    Optional<Member> findById(Long memberId);

    Member save(Member member);

    List<Member> saveAll(List<Member> members);

    void deleteAllInBatch();
}
