package corp.kora.auth.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import corp.kora.auth.domain.provider.TokenProvider;
import corp.kora.auth.domain.service.AuthService;
import corp.kora.auth.infrastructure.provider.TokenProviderV1;
import corp.kora.auth.infrastructure.service.AuthServiceV1;
import corp.kora.member.domain.repository.MemberQueryRepository;

@Configuration
public class AuthConfig {

	@Bean
	public AuthService authService(TokenProvider tokenProvider, MemberQueryRepository memberQueryRepository) {
		return new AuthServiceV1(tokenProvider, memberQueryRepository);
	}

	@Bean
	public TokenProvider tokenProvider(
		@Value("${security.jwt.token.secret.key}") final String secretKey,
		@Value("${security.jwt.token.access.expires}") final long accessTokenExpirationInMilliseconds,
		@Value("${security.jwt.token.refresh.expires}") final long refreshTokenExpirationInMilliseconds
	) {
		return new TokenProviderV1(
			secretKey,
			accessTokenExpirationInMilliseconds,
			refreshTokenExpirationInMilliseconds
		);
	}
}
