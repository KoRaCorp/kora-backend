package corp.kora.auth.domain.service.output;

public record AuthToken(
	String accessToken,
	String refreshToken
) {
}
