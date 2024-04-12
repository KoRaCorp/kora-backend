package corp.kora.api.member.presentation.response;

import corp.kora.member.domain.model.MemberReadModel;

public record MemberReadByIdResponse(
        Long memberId,
        String nickname,
        String email,
        String profileMessage,
        String profileImageUrl
) {

    public static MemberReadByIdResponse from(MemberReadModel memberReadModel) {
        return new MemberReadByIdResponse(
                memberReadModel.memberId(),
                memberReadModel.nickname(),
                memberReadModel.email(),
                memberReadModel.profileMessage(),
                memberReadModel.profileImageUrl()
        );
    }
}
