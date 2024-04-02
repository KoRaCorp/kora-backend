package corp.kora.member.infrastructure.service;

import corp.kora.member.domain.exception.MemberNotFoundException;
import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.repository.MemberCommandRepository;
import corp.kora.member.domain.repository.MemberQueryRepository;
import corp.kora.member.domain.service.MemberService;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class MemberServiceV1 implements MemberService {
    private final MemberQueryRepository memberQueryRepository;
    private final MemberCommandRepository memberCommandRepository;
    @Override
    public Member find(Long memberId) {
        return memberQueryRepository.find(memberId).orElseThrow(
                () -> new MemberNotFoundException("member not found")
        );
    }
}
