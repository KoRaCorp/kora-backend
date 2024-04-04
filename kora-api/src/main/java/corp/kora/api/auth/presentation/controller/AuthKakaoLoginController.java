package corp.kora.api.auth.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import corp.kora.api.auth.application.AuthKakaoLoginProcessor;
import corp.kora.api.auth.presentation.request.AuthKakaoLoginRequest;
import corp.kora.api.auth.presentation.response.AuthKakaoLoginResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthKakaoLoginController {
	private final AuthKakaoLoginProcessor authKakaoLoginProcessor;

	@PostMapping("/api/auth/kakao/login")
	@ResponseStatus(HttpStatus.OK)
	public AuthKakaoLoginResponse login(
		@RequestBody AuthKakaoLoginRequest request
	) {
		return authKakaoLoginProcessor.execute(request.code());
	}
}
