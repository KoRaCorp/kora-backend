package corp.kora.auth.infrastructure.service;

import corp.kora.auth.domain.provider.TokenProvider;
import corp.kora.auth.domain.service.AuthService;
import corp.kora.auth.domain.service.output.AuthToken;
import corp.kora.member.domain.exception.NotFoundMemberException;
import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthServiceV1 implements AuthService {
	private final TokenProvider tokenProvider;
	private final MemberQueryRepository memberQueryRepository;

	@Override
	public AuthToken login(Long memberId) {
		Member foundMember = memberQueryRepository.find(memberId)
			.orElseThrow(() -> new NotFoundMemberException("Not Found Member"));

		String accessToken = tokenProvider.createAccessToken(String.valueOf(memberId));
		String refreshToken = tokenProvider.createRefreshToken(String.valueOf(memberId));

		foundMember.updateRefreshToken(refreshToken);

		return new AuthToken(accessToken, refreshToken);
	}
}
