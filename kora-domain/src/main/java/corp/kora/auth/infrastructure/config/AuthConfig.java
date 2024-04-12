package corp.kora.auth.infrastructure.config;

import corp.kora.auth.domain.provider.TokenProvider;
import corp.kora.auth.infrastructure.provider.TokenProviderV1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

    @Bean
    public TokenProvider tokenProvider(
            @Value("${security.jwt.token.secret-key}") final String secretKey,
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
