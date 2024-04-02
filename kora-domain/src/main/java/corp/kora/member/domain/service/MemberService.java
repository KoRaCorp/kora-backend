package corp.kora.member.domain.service;

import corp.kora.member.domain.model.Member;

public interface MemberService {
    Member find(Long memberId);
}
