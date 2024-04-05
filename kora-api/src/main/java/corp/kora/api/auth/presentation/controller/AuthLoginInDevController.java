package corp.kora.api.auth.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import corp.kora.api.auth.application.AuthLoginInDevProcessor;
import corp.kora.api.auth.presentation.request.AuthLoginInDevRequest;
import corp.kora.api.auth.presentation.response.AuthLoginInDevResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthLoginInDevController {
	private final AuthLoginInDevProcessor authLoginInDevProcessor;

	@PostMapping("/api/auth/login-in-dev")
	@ResponseStatus(HttpStatus.OK)
	public AuthLoginInDevResponse loginInDev(
		@RequestBody AuthLoginInDevRequest request
	) {
		return authLoginInDevProcessor.execute(request.authKey());

	}
}
