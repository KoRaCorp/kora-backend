package corp.kora.api.auth.application;

import corp.kora.auth.domain.model.AuthTokenModel;
import corp.kora.auth.domain.provider.TokenProvider;
import corp.kora.member.domain.exception.NotFoundMemberException;
import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public abstract class LoginProcessor {
    protected final MemberRepository memberRepository;
    protected final TokenProvider tokenProvider;

    protected AuthTokenModel login(Long memberId) {
        Member foundMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException("Not Found Member"));

        String accessToken = tokenProvider.createAccessToken(String.valueOf(memberId));
        String refreshToken = tokenProvider.createRefreshToken(String.valueOf(memberId));

        foundMember.updateRefreshToken(refreshToken);

        return new AuthTokenModel(accessToken, refreshToken);
    }
}
