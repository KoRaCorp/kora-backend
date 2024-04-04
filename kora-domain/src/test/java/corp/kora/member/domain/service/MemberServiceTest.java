package corp.kora.member.domain.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import corp.kora.member.domain.service.input.MemberSignUpIfAbsentInput;
import corp.kora.member.infrastructure.service.MemberServiceV1;
import corp.kora.support.ServiceTestSupport;

class MemberServiceTest extends ServiceTestSupport {

	private MemberService memberService;

	@BeforeEach
	void setUp() {
		memberService = new MemberServiceV1(memberQueryRepository, memberCommandRepository);
	}

	@DisplayName("정상적으로 회원가입에 성공한다.")
	@Test
	void signUpIfAbsent() {
		// given
		String authKey = "auth-key";
		String email = "example@gmail.com";

		MemberSignUpIfAbsentInput input = new MemberSignUpIfAbsentInput(email, authKey);

		// when
		Long memberId = memberService.signUpIfAbsent(input);
		// then

		assertThat(memberId).isNotNull();
	}

	@DisplayName("이미 회원가입이 되어 있는 경우 아무 처리도 하지 않는다.")
	@Test
	void signUpIfAbsent2() {
		// given
		String authKey = "auth-key";
		String email = "example@gmail.com";

		MemberSignUpIfAbsentInput input = new MemberSignUpIfAbsentInput(email, authKey);
		Long memberId = memberService.signUpIfAbsent(input);

		// when
		Long memberIdToCompare = memberService.signUpIfAbsent(input);

		// then
		assertThat(memberId).isEqualTo(memberIdToCompare);
	}

}
