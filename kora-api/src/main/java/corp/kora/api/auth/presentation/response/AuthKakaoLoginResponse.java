package corp.kora.api.auth.presentation.response;

import corp.kora.auth.domain.service.output.AuthToken;

public record AuthKakaoLoginResponse(
	String accessToken,
	String refreshToken
) {

	public static AuthKakaoLoginResponse from(AuthToken authToken) {
		return new AuthKakaoLoginResponse(authToken.accessToken(), authToken.refreshToken());
	}
}
