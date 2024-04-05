package corp.kora.api.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import corp.kora.api.auth.presentation.response.AuthLoginInDevResponse;
import corp.kora.auth.domain.service.AuthService;
import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.service.MemberService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthLoginInDevProcessor {
	private final MemberService memberService;
	private final AuthService authService;

	@Transactional
	public AuthLoginInDevResponse execute(String authKey) {
		Member foundMember = memberService.find(authKey);
		return AuthLoginInDevResponse.from(authService.login(foundMember.getId()));
	}

}
