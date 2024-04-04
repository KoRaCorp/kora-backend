package corp.kora.member.infrastructure.service;

import corp.kora.member.domain.exception.MemberNotFoundException;
import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.repository.MemberCommandRepository;
import corp.kora.member.domain.repository.MemberQueryRepository;
import corp.kora.member.domain.service.MemberService;
import corp.kora.member.domain.service.input.MemberSignUpIfAbsentInput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberServiceV1 implements MemberService {
	private final MemberQueryRepository memberQueryRepository;
	private final MemberCommandRepository memberCommandRepository;

	@Override
	public Member find(String authKey) {
		return memberQueryRepository.find(authKey).orElseThrow(
			() -> new MemberNotFoundException("member not found")
		);
	}

	@Override
	public Long signUpIfAbsent(MemberSignUpIfAbsentInput input) {
		Member member = memberQueryRepository.find(input.authKey())
			.orElseGet(() -> {
				Member memberToSave = Member
					.builder()
					.email(input.email())
					.nickname(null)
					.authKey(input.authKey())
					.build();

				return memberCommandRepository.save(memberToSave);
			});

		// Email은 변경이 될 수 있다.
		member.changeEmailIfNotSame(input.email());
		return member.getId();
	}

	@Override
	public Member find(Long memberId) {
		return memberQueryRepository.find(memberId).orElseThrow(
			() -> new MemberNotFoundException("member not found")
		);
	}

}
