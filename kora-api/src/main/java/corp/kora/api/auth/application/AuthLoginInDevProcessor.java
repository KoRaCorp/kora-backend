package corp.kora.api.auth.application;

import corp.kora.api.auth.presentation.response.AuthLoginInDevResponse;
import corp.kora.auth.domain.provider.TokenProvider;
import corp.kora.global.exception.NoAccessAuthenticationException;
import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthLoginInDevProcessor extends LoginProcessor {
    public AuthLoginInDevProcessor(MemberRepository memberRepository, TokenProvider tokenProvider) {
        super(memberRepository, tokenProvider);
    }

    @Transactional
    public AuthLoginInDevResponse execute(String authKey) {
        Member foundMember = memberRepository.findByAuthKey(authKey)
                .orElseThrow(() -> new NoAccessAuthenticationException("member not found"));

        return AuthLoginInDevResponse.from(login(foundMember.getId()));
    }

}
