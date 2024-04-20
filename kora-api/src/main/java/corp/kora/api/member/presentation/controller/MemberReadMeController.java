package corp.kora.api.member.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import corp.kora.api.member.application.MemberReadMeManager;
import corp.kora.api.member.presentation.response.MemberReadMeResponse;
import corp.kora.global.annotation.LoginMemberId;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberReadMeController {
	private final MemberReadMeManager memberReadMeManager;

	@GetMapping("/api/members/me")
	@ResponseStatus(HttpStatus.OK)
	public MemberReadMeResponse readById(
		@LoginMemberId Long memberId
	) {
		return memberReadMeManager.read(new MemberReadMeManager.Query(memberId));
	}
}
