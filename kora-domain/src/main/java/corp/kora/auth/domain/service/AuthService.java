package corp.kora.auth.domain.service;

import corp.kora.auth.domain.service.output.AuthToken;

public interface AuthService {
	AuthToken login(Long memberId);
}
