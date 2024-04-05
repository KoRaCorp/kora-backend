package corp.kora.member.infrastructure.provider;

import java.util.Arrays;

import org.springframework.util.StringUtils;

import corp.kora.member.domain.exception.InvalidEmailException;
import corp.kora.member.domain.provider.NicknameProvider;

// email prefix에 . 이 포함되어 있으면 nickname suffix를 추출하고, 없으면 a를 반환한다.
public class NicknameProviderV1 implements NicknameProvider {
	private static final String NICKNAME_SUFFIX_DELIMITER = ".";
	private static final String EMAIL_DELIMITER = "@";

	@Override
	public String generateNextNicknameSuffix(String currentNicknameSuffix) {
		if (currentNicknameSuffix == null || currentNicknameSuffix.isEmpty()) {
			return "a";
		}

		char[] suffix = currentNicknameSuffix.toCharArray();
		final int size = suffix.length;

		for (int index = size - 1; index >= 0; index--) {
			// 끝자리가 z가 아니면 추가하고 마무리
			if (suffix[index] != 'z') {
				suffix[index]++;
				break;
			}

			// 끝자리가 z이고, 앞자리가 없으면 'a'를 추가하고 마무리 한다.
			if (index == 0) {
				suffix[index] = 'a';
				suffix = Arrays.copyOf(suffix, size + 1);
				suffix[size] = 'a';
				break;
			}

			// 끝자리가 z이고 앞자리가 있으면 다음 스텝을 진행한다.
			suffix[index] = 'a';
		}

		return String.valueOf(suffix);
	}

	@Override
	public String getNicknameSuffixDelimiter() {
		return NICKNAME_SUFFIX_DELIMITER;
	}

	@Override
	public String extractNickname(String email) {
		validateEmail(email);
		return email.split(EMAIL_DELIMITER)[0];
	}

	private void validateEmail(String email) {
		if (!StringUtils.hasText(email)) {
			throw new InvalidEmailException("이메일 형식이 올바르지 않습니다.");
		}

		if (!email.contains(EMAIL_DELIMITER)) {
			throw new InvalidEmailException("이메일 형식이 올바르지 않습니다.");
		}
	}
}
