package corp.kora.member.domain.provider;

public interface NicknameProvider {
	String generateNextNicknameSuffix(final String currentNicknameSuffix);

	String getNicknameSuffixDelimiter();

	String extractNickname(final String email);

}


