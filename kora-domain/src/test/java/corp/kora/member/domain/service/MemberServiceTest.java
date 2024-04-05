package corp.kora.member.domain.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.provider.NicknameProvider;
import corp.kora.member.domain.service.input.MemberSignUpIfAbsentInput;
import corp.kora.member.infrastructure.provider.NicknameProviderV1;
import corp.kora.member.infrastructure.service.MemberServiceV1;
import corp.kora.support.ServiceTestSupport;

class MemberServiceTest extends ServiceTestSupport {

	private MemberService memberService;

	@BeforeEach
	void setUp() {
		NicknameProvider nicknameProvider = new NicknameProviderV1();
		memberService = new MemberServiceV1(memberQueryRepository, memberCommandRepository, nicknameProvider);

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
		Member member = memberService.find(memberId);
		// then

		assertThat(memberId).isNotNull();
		assertThat(member.getNickname()).isEqualTo("example");
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

	@DisplayName("동일한 닉네임으로 생성된 회원일 경우 닉네임에 suffix를 알파벳 순서대로 추가한다.")
	@Test
	void signUpIfAbsent3() {
		MemberSignUpIfAbsentInput input1 = new MemberSignUpIfAbsentInput("uncle.ra@gmail.com", "uncle-ra-1-auth-key");
		Member uncleRa1 = memberService.find(memberService.signUpIfAbsent(input1));

		MemberSignUpIfAbsentInput input2 = new MemberSignUpIfAbsentInput("uncle.ra@gmail.com", "uncle-ra-2-auth-key");
		Member uncleRa2 = memberService.find(memberService.signUpIfAbsent(input2));

		MemberSignUpIfAbsentInput input3 = new MemberSignUpIfAbsentInput("uncle.ra@gmail.com", "uncle-ra-3-auth-key");
		Member uncleRa3 = memberService.find(memberService.signUpIfAbsent(input3));

		// then
		assertThat(uncleRa1.getNickname()).isEqualTo("uncle.ra");
		assertThat(uncleRa2.getNickname()).isEqualTo("uncle.ra.a");
		assertThat(uncleRa3.getNickname()).isEqualTo("uncle.ra.b");
	}

}
