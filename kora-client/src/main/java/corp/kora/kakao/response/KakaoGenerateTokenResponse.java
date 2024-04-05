package corp.kora.kakao.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoGenerateTokenResponse(
	@JsonProperty("access_token") String accessToken,
	@JsonProperty("token_type") String tokenType,
	@JsonProperty("refresh_token") String refreshToken,
	@JsonProperty("expires_in") int expiresIn,
	@JsonProperty("scope") String scope,
	@JsonProperty("refresh_token_expires_in") int refreshTokenExpiresIn,
	@JsonProperty("id_token") String idToken
) {
}
