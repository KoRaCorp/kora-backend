package corp.kora.auth.domain.model;

public record AuthTokenModel(
        String accessToken,
        String refreshToken
) {
}
