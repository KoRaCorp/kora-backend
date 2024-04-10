package corp.kora.auth.infrastructure.provider;

import java.security.Key;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

import corp.kora.auth.domain.provider.TokenProvider;
import corp.kora.global.exception.NoAccessAuthorizationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	@Override
	public String extractPayload(String authorization) {
		return extractPayloadByAccessToken(removeBearer(authorization));
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

	private String removeBearer(String authorization) {
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			throw new NoAccessAuthorizationException("토큰 형식이 올바르지 않습니다.");
		}
		return authorization.substring(7);
	}

	private String extractPayloadByAccessToken(String accessToken) {
		return getSubject(accessToken);
	}

	private String getSubject(String token) {
		return parseToken(token).getBody().getSubject();
	}

	private Jws<Claims> parseToken(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			throw new NoAccessAuthorizationException("만료된 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			throw new NoAccessAuthorizationException("지원되지 않는 토큰입니다.");
		} catch (MalformedJwtException e) {
			throw new NoAccessAuthorizationException("Malformed된 토큰입니다.");
		} catch (SignatureException e) {
			throw new NoAccessAuthorizationException("Signature가 일치하지 않습니다.");
		} catch (IllegalArgumentException e) {
			throw new NoAccessAuthorizationException("JWT 토큰이 잘못되었습니다.");
		} catch (Exception e) {
			log.error("Exception: {}", e.getMessage());
			throw new NoAccessAuthorizationException("Exception! :JWT 토큰이 잘못되었습니다.");
		}
	}
}
