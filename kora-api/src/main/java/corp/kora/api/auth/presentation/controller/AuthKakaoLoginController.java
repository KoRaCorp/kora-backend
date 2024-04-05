package corp.kora.api.auth.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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

	/**
	 * https://kauth.kakao.com/oauth/authorize?client_id={clientId}&redirect_uri=http://localhost:8080/api/auth/kakao/login&response_type=code
	 */
	@GetMapping("/api/auth/kakao/login")
	@ResponseStatus(HttpStatus.OK)
	public AuthKakaoLoginResponse login(
		AuthKakaoLoginRequest request
	) {
		return authKakaoLoginProcessor.execute(request.code());
	}
}
