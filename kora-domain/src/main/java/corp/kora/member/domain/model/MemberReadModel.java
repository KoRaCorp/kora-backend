package corp.kora.member.domain.model;

public record MemberReadModel(
        Long memberId,
        String email,
        String nickname,
        String profileMessage,
        String profileImageFilePath
) {

    public String profileImageUrl() {
        return null;
    }
}
