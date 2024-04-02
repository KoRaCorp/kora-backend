package corp.kora.api.member.presentation.response;

import corp.kora.member.domain.model.Member;

public record MemberFindByIdResponse(
        Long memberId,
        String nickname,
        String email,
        String profileMessage,
        String profileImageUrl
) {

    public static MemberFindByIdResponse from(Member member) {
        return new MemberFindByIdResponse(
                member.getId(),
                member.getNickname(),
                member.getEmail(),
                member.getProfileMessage(),
                member.profileImageUrl()
        );
    }
}
