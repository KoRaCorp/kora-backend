package corp.kora.api.auth.presentation.response;

import corp.kora.auth.domain.service.output.AuthToken;

public record AuthLoginInDevResponse(
	String accessToken,
	String refreshToken
) {
	public static AuthLoginInDevResponse from(AuthToken authToken) {
		return new AuthLoginInDevResponse(authToken.accessToken(), authToken.refreshToken());
	}
}
