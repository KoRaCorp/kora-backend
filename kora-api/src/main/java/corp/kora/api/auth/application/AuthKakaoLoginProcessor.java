package corp.kora.api.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import corp.kora.api.auth.presentation.response.AuthKakaoLoginResponse;
import corp.kora.auth.domain.service.AuthService;
import corp.kora.kakao.client.KakaoFetchMemberInfoClient;
import corp.kora.kakao.client.KakaoGenerateTokenClient;
import corp.kora.kakao.response.KakaoFetchMemberInfoResponse;
import corp.kora.kakao.response.KakaoGenerateTokenResponse;
import corp.kora.member.domain.service.MemberService;
import corp.kora.member.domain.service.input.MemberSignUpIfAbsentInput;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthKakaoLoginProcessor {
	private final KakaoFetchMemberInfoClient kakaoFetchMemberInfoClient;
	private final KakaoGenerateTokenClient kakaoGenerateTokenClient;
	private final MemberService memberService;
	private final AuthService authService;

	@Transactional
	public AuthKakaoLoginResponse execute(String code) {
		// kakao server로 부터 인가 코드를 통해 토큰 발급 받는다.
		KakaoGenerateTokenResponse generateTokenResponse = kakaoGenerateTokenClient.generateToken(code);

		// kakao server로 부터 회원 정보 얻는다.
		KakaoFetchMemberInfoResponse memberInfoResponse = kakaoFetchMemberInfoClient.fetchMemberInfo(
			generateTokenResponse.accessToken());

		// 회원이 존재하지 않을 경우 회원가입 시킨다.
		Long memberId = memberService.signUpIfAbsent(MemberSignUpIfAbsentInput.from(memberInfoResponse));

		// 로그인
		return AuthKakaoLoginResponse.from(authService.login(memberId));
	}

}
