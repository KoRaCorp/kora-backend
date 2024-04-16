package corp.kora.member.domain.generator;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import corp.kora.member.domain.generater.NicknameGenerator;
import corp.kora.member.infrastructure.generator.NicknameGeneratorV1;
import corp.kora.support.ServiceTestSupport;

public class NicknameGeneratorTest extends ServiceTestSupport {
	private NicknameGenerator nicknameGenerator;

	@BeforeEach
	void setUp() {
		nicknameGenerator = new NicknameGeneratorV1(memberRepository);
	}

	@DisplayName("기존에 사용 중인 동일한 닉네임이 없을 경우 이메일에서 추출한 닉네임을 그대로 사용한다.")
	@Test
	void generateNickname() {
		// given
		final String email = "uncle.ra@gmail.com";

		// when & then
		assertThat(nicknameGenerator.generateNickname(email)).isEqualTo("uncle.ra");
	}

	@DisplayName("기존에 사용 중인 동일한 닉네임이 존재할 경우 이메일에서 추출한 닉네임에 .a를 붙힌다.")
	@Test
	void generateNickname2() {
		// given
		final String email = "uncle.ra@gmail.com";
		generateMember(email);

		// when & then
		assertThat(nicknameGenerator.generateNickname(email)).isEqualTo("uncle.ra.a");
	}

	@DisplayName("기존에 사용 중인 동일한 닉네임이 존재할 경우 이메일에서 추출한 닉네임에 uncle.ra.a까지 생성된 경우 uncle.ra.b를 생성한다.")
	@Test
	void generateNickname3() {
		// given
		generateMember("uncle.ra@gmail.com");
		generateMember("uncle.ra@gmail.com");

		// when & then
		assertThat(nicknameGenerator.generateNickname("uncle.ra@gmail.com")).isEqualTo("uncle.ra.b");
	}

}
