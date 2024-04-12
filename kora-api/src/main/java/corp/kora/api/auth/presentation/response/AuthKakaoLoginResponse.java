package corp.kora.api.auth.presentation.response;

import corp.kora.auth.domain.model.AuthTokenModel;

public record AuthKakaoLoginResponse(
        String accessToken,
        String refreshToken
) {

    public static AuthKakaoLoginResponse from(AuthTokenModel authToken) {
        return new AuthKakaoLoginResponse(authToken.accessToken(), authToken.refreshToken());
    }
}
