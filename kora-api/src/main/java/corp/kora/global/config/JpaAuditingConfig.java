package corp.kora.global.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import corp.kora.auth.domain.provider.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing
public class JpaAuditingConfig {

	private static final String AUTHORIZATION = "Authorization";
	private static final String UNKNOWN = "UNKNOWN";

	private final HttpServletRequest httpServletRequest;
	private final TokenProvider tokenProvider;

	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAware<>() {
			@Override
			public Optional<String> getCurrentAuditor() {
				try {
					final String authorization = httpServletRequest.getHeader(AUTHORIZATION);
					return authorization != null ? extractMemberId(authorization) : Optional.of(UNKNOWN);
				} catch (IllegalStateException e) {
					return Optional.of(UNKNOWN);
				}
			}

			private Optional<String> extractMemberId(String authorization) {
				return Optional.of(tokenProvider.extractPayload(authorization));
			}
		};
	}
}
