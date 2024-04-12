package corp.kora.api.auth.application;

import corp.kora.api.auth.presentation.response.AuthLoginInDevResponse;
import corp.kora.auth.domain.provider.TokenProvider;
import corp.kora.auth.domain.service.output.AuthToken;
import corp.kora.global.exception.NoAccessAuthenticationException;
import corp.kora.member.domain.exception.NotFoundMemberException;
import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthLoginInDevProcessor {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public AuthLoginInDevResponse execute(String authKey) {
        Member foundMember = memberRepository.findByAuthKey(authKey)
                .orElseThrow(() -> new NoAccessAuthenticationException("member not found"));

        return AuthLoginInDevResponse.from(login(foundMember.getId()));
    }


    private AuthToken login(Long memberId) {
        Member foundMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException("Not Found Member"));

        String accessToken = tokenProvider.createAccessToken(String.valueOf(memberId));
        String refreshToken = tokenProvider.createRefreshToken(String.valueOf(memberId));

        foundMember.updateRefreshToken(refreshToken);

        return new AuthToken(accessToken, refreshToken);
    }

}
