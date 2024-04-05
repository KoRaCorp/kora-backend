package corp.kora.member.domain.provider;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import corp.kora.member.domain.exception.InvalidEmailException;
import corp.kora.member.infrastructure.provider.NicknameProviderV1;

class NicknameProviderTest {

	private NicknameProvider nicknameProvider;

	@BeforeEach
	void setUp() {
		nicknameProvider = new NicknameProviderV1();
	}

	@DisplayName("이메일에서 닉네임을 추출한다.")
	@Test
	void extractNickname() {
		// given
		final String email = "uncle.ra@gmail.com";

		// when & then
		assertThat(nicknameProvider.extractNickname(email)).isEqualTo("uncle.ra");
	}

	@DisplayName("이메일 형식이 올바르지 않을 경우 예외를 발생시킨다.")
	@Test
	void extractNickname2() {
		// given
		final String email = "uncle.ra!gmail.com";

		// when & then
		assertThatThrownBy(() -> {
			nicknameProvider.extractNickname(email);
		}).isInstanceOf(InvalidEmailException.class);
	}

	@DisplayName("다음 닉네임 접미사를 생성한다. (초기값)")
	@Test
	void generateNextNicknameSuffix() {
		// when & then

		assertThat(nicknameProvider.generateNextNicknameSuffix("")).isEqualTo("a");
	}

	@DisplayName("다음 닉네임 접미사를 생성한다 a -> b)")
	@Test
	void generateNextNicknameSuffix2() {
		// when & then

		assertThat(nicknameProvider.generateNextNicknameSuffix("a")).isEqualTo("b");
	}

	@DisplayName("다음 닉네임 접미사를 생성한다 z -> aa)")
	@Test
	void generateNextNicknameSuffix3() {
		// when & then
		assertThat(nicknameProvider.generateNextNicknameSuffix("z")).isEqualTo("aa");
	}

	@DisplayName("다음 닉네임 접미사를 생성한다 azz -> baa)")
	@Test
	void generateNextNicknameSuffix4() {
		// when & then
		assertThat(nicknameProvider.generateNextNicknameSuffix("azz")).isEqualTo("baa");
	}
}
