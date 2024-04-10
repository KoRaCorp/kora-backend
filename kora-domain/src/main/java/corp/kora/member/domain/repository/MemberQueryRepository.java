package corp.kora.member.domain.repository;

import java.util.Optional;

import corp.kora.member.domain.model.Member;

public interface MemberQueryRepository {
	Optional<Member> find(Long memberId);

	Optional<Member> find(String authKey);

	Optional<Member> findByNickname(String nickname);

	Optional<Member> findLastNicknameSuffix(String nickname, String delimiter);

	Optional<Member> findById(Long memberId);
}
