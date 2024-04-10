package corp.kora.auth.domain.provider;

public interface TokenProvider {

	String createAccessToken(String subject);

	String createRefreshToken(String subject);

	String extractPayload(String authorization);
}
