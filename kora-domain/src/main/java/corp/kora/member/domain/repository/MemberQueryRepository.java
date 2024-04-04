package corp.kora.member.domain.repository;

import java.util.Optional;

import corp.kora.member.domain.model.Member;

public interface MemberQueryRepository {
	Optional<Member> find(Long memberId);

	Optional<Member> find(String authKey);
}
