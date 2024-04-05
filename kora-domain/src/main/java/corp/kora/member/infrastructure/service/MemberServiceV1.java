package corp.kora.member.infrastructure.service;

import java.util.Optional;

import corp.kora.member.domain.exception.MemberNotFoundException;
import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.provider.NicknameProvider;
import corp.kora.member.domain.repository.MemberCommandRepository;
import corp.kora.member.domain.repository.MemberQueryRepository;
import corp.kora.member.domain.service.MemberService;
import corp.kora.member.domain.service.input.MemberSignUpIfAbsentInput;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberServiceV1 implements MemberService {
	private final MemberQueryRepository memberQueryRepository;
	private final MemberCommandRepository memberCommandRepository;
	private final NicknameProvider nicknameProvider;

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
					.nickname(generateNickname(input.email()))
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
	
	/**
	 * 이메일 기반으로 닉네임을 생성한다.
	 */
	private String generateNickname(String email) {
		String nickname = nicknameProvider.extractNickname(email);
		if (memberQueryRepository.findByNickname(nickname).isEmpty()) {
			return nickname;
		}

		Optional<Member> memberOptional = memberQueryRepository.findLastNicknameSuffix(nickname,
			nicknameProvider.getNicknameSuffixDelimiter());

		final String lastNicknameSuffix = memberOptional.map(
			member -> member.getNickname().substring(nickname.length() + 1)).orElse(null);

		return new StringBuilder()
			.append(nickname)
			.append(nicknameProvider.getNicknameSuffixDelimiter())
			.append(nicknameProvider.generateNextNicknameSuffix(lastNicknameSuffix))
			.toString();
	}

}
