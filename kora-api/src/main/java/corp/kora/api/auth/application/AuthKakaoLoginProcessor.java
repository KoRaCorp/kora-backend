package corp.kora.api.auth.application;

import corp.kora.api.auth.presentation.response.AuthKakaoLoginResponse;
import corp.kora.auth.domain.provider.TokenProvider;
import corp.kora.auth.domain.service.output.AuthToken;
import corp.kora.kakao.client.KakaoFetchMemberInfoClient;
import corp.kora.kakao.client.KakaoGenerateTokenClient;
import corp.kora.kakao.response.KakaoFetchMemberInfoResponse;
import corp.kora.kakao.response.KakaoGenerateTokenResponse;
import corp.kora.member.domain.exception.NotFoundMemberException;
import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.provider.NicknameProvider;
import corp.kora.member.domain.repository.MemberRepository;
import corp.kora.member.domain.service.input.MemberSignUpIfAbsentInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthKakaoLoginProcessor {
    private final KakaoFetchMemberInfoClient kakaoFetchMemberInfoClient;
    private final KakaoGenerateTokenClient kakaoGenerateTokenClient;
    private final MemberRepository memberRepository;
    private final NicknameProvider nicknameProvider;
    private final TokenProvider tokenProvider;

    @Transactional
    public AuthKakaoLoginResponse execute(String code) {
        // kakao server로 부터 인가 코드를 통해 토큰 발급 받는다.
        KakaoGenerateTokenResponse generateTokenResponse = kakaoGenerateTokenClient.generateToken(code);

        // kakao server로 부터 회원 정보 얻는다.
        KakaoFetchMemberInfoResponse memberInfoResponse = kakaoFetchMemberInfoClient.fetchMemberInfo(
                generateTokenResponse.accessToken());

        // 회원이 존재하지 않을 경우 회원가입 시킨다.
        Long memberId = signUpIfAbsent(MemberSignUpIfAbsentInput.from(memberInfoResponse));

        // 로그인
        return AuthKakaoLoginResponse.from(login(memberId));
    }


    private AuthToken login(Long memberId) {
        Member foundMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException("Not Found Member"));

        String accessToken = tokenProvider.createAccessToken(String.valueOf(memberId));
        String refreshToken = tokenProvider.createRefreshToken(String.valueOf(memberId));

        foundMember.updateRefreshToken(refreshToken);

        return new AuthToken(accessToken, refreshToken);
    }

    private Long signUpIfAbsent(MemberSignUpIfAbsentInput input) {
        Member member = memberRepository.findByAuthKey(input.authKey())
                .orElseGet(() -> {
                    Member memberToSave = Member
                            .builder()
                            .email(input.email())
                            .nickname(generateNickname(input.email()))
                            .authKey(input.authKey())
                            .build();

                    return memberRepository.save(memberToSave);
                });

        // Email은 변경이 될 수 있다.
        member.changeEmailIfNotSame(input.email());
        return member.getId();
    }

    /**
     * 이메일 기반으로 닉네임을 생성한다.
     */
    private String generateNickname(String email) {
        String nickname = nicknameProvider.extractNickname(email);
        if (memberRepository.findByNickname(nickname).isEmpty()) {
            return nickname;
        }

        Optional<Member> memberOptional = memberRepository.findLastNicknameSuffix(nickname,
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
