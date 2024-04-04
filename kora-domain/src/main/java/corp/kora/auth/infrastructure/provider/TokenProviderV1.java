package corp.kora.auth.infrastructure.provider;

import java.security.Key;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

import corp.kora.auth.domain.provider.TokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class TokenProviderV1 implements TokenProvider {
	private final Key key;
	private final long accessTokenExpirationInMilliseconds;
	private final long refreshTokenExpirationInMilliseconds;

	public TokenProviderV1(
		final String secretKey, long accessTokenExpirationInMilliseconds, long refreshTokenExpirationInMilliseconds) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.accessTokenExpirationInMilliseconds = accessTokenExpirationInMilliseconds;
		this.refreshTokenExpirationInMilliseconds = refreshTokenExpirationInMilliseconds;
	}

	public String createAccessToken(String subject) {
		return createToken(subject, accessTokenExpirationInMilliseconds);
	}

	public String createRefreshToken(String subject) {
		return createToken(subject, refreshTokenExpirationInMilliseconds);
	}

	private String createToken(final String subject, final Long expirationInMilliseconds) {
		final String randomString = "rds";
		Date now = new Date();
		Date validity = new Date(now.getTime() + expirationInMilliseconds);

		return Jwts.builder()
			.setSubject(subject)
			.setHeaderParam(randomString, RandomStringUtils.randomAlphanumeric(5))
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}
}
