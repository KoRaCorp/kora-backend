package corp.kora.member.domain.service;

import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.service.input.MemberSignUpIfAbsentInput;

public interface MemberService {
	Member find(Long memberId);

	Long signUpIfAbsent(MemberSignUpIfAbsentInput input);
	
}
