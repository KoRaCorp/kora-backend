package corp.kora.member.infrastructure.generator;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.util.StringUtils;

import corp.kora.member.domain.exception.InvalidEmailException;
import corp.kora.member.domain.generater.NicknameGenerator;
import corp.kora.member.domain.model.Member;
import corp.kora.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NicknameGeneratorV1 implements NicknameGenerator {
	private final MemberRepository memberRepository;

	private static final String NICKNAME_SUFFIX_DELIMITER = ".";
	private static final String EMAIL_DELIMITER = "@";

	@Override
	public String generateNickname(String email) {
		String nickname = extractNickname(email);
		if (memberRepository.findByNickname(nickname).isEmpty()) {
			return nickname;
		}

		Optional<Member> memberOptional = memberRepository.findLastNicknameSuffix(nickname,
			getNicknameSuffixDelimiter());

		final String lastNicknameSuffix = memberOptional.map(
			member -> member.getNickname().substring(nickname.length() + 1)).orElse(null);

		return new StringBuilder()
			.append(nickname)
			.append(getNicknameSuffixDelimiter())
			.append(generateNextNicknameSuffix(lastNicknameSuffix))
			.toString();
	}

	private String generateNextNicknameSuffix(String currentNicknameSuffix) {
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

	private String getNicknameSuffixDelimiter() {
		return NICKNAME_SUFFIX_DELIMITER;
	}

	private String extractNickname(String email) {
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
