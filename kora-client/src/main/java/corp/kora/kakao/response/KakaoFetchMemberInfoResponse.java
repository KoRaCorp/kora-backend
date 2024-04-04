package corp.kora.kakao.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoFetchMemberInfoResponse(
	@JsonProperty("id") Long id,
	@JsonProperty("connected_at") String connectedAt,
	@JsonProperty("kakao_account") KakaoAccount kakaoAccount
) {

	record KakaoAccount(
		@JsonProperty("name_needs_agreement") Boolean nameNeedsAgreement,
		@JsonProperty("name") String name,
		@JsonProperty("has_email") Boolean hasEmail,
		@JsonProperty("email_needs_agreement") Boolean emailNeedsAgreement,
		@JsonProperty("is_email_valid") Boolean isEmailValid,
		@JsonProperty("is_email_verified") Boolean isEmailVerified,
		@JsonProperty("email") String email,
		@JsonProperty("has_gender") Boolean hasGender,
		@JsonProperty("gender_needs_agreement") Boolean genderNeedsAgreement,
		@JsonProperty("gender") String gender) {
	}
}
