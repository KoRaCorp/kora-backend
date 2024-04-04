package corp.kora.api.member.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import corp.kora.api.member.application.MemberFindByIdQueryManager;
import corp.kora.api.member.presentation.response.MemberFindByIdResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberFindByIdController {
	private final MemberFindByIdQueryManager memberFindByIdQueryManager;

	@GetMapping("/api/members/{memberId}")
	@ResponseStatus(HttpStatus.OK)
	public MemberFindByIdResponse findById(
		@PathVariable Long memberId
	) {
		return memberFindByIdQueryManager.read(new MemberFindByIdQueryManager.Query(memberId));
	}
}
