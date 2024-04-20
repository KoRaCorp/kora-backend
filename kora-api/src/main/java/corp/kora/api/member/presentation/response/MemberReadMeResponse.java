package corp.kora.api.member.presentation.response;

import corp.kora.member.domain.model.MemberReadModel;

public record MemberReadMeResponse(
	Long memberId,
	String nickname,
	String email,
	String profileMessage,
	String profileImageUrl
) {

	public static MemberReadMeResponse from(MemberReadModel memberReadModel) {
		return new MemberReadMeResponse(
			memberReadModel.memberId(),
			memberReadModel.nickname(),
			memberReadModel.email(),
			memberReadModel.profileMessage(),
			memberReadModel.profileImageUrl()
		);
	}
}
