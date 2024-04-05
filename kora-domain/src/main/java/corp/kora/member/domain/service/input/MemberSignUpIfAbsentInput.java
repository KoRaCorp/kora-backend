package corp.kora.member.domain.service.input;

import corp.kora.kakao.response.KakaoFetchMemberInfoResponse;

public record MemberSignUpIfAbsentInput(
	String email,
	String authKey
) {

	public static MemberSignUpIfAbsentInput from(KakaoFetchMemberInfoResponse response) {
		return new MemberSignUpIfAbsentInput(response.kakaoAccount().email(), response.id().toString());
	}
}
