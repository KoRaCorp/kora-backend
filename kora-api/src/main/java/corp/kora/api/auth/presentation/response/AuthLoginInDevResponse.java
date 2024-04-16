package corp.kora.api.auth.presentation.response;

import corp.kora.auth.domain.model.AuthTokenModel;

public record AuthLoginInDevResponse(
        String accessToken,
        String refreshToken
) {
    public static AuthLoginInDevResponse from(AuthTokenModel authToken) {
        return new AuthLoginInDevResponse(authToken.accessToken(), authToken.refreshToken());
    }
}
