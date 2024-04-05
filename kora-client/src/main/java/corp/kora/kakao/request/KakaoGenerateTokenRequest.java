package corp.kora.kakao.request;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoGenerateTokenRequest(
	@JsonProperty("client_id") String clientId,
	@JsonProperty("grant_type") String grantType,
	@JsonProperty("redirect_uri") String redirectUri,
	@JsonProperty("code") String code
) {

	public MultiValueMap<String, String> toFormData() {
		LinkedMultiValueMap<String, String> formDataMap = new LinkedMultiValueMap<>();
		formDataMap.add("grant_type", grantType);
		formDataMap.add("client_id", clientId);
		formDataMap.add("redirect_uri", redirectUri);
		formDataMap.add("code", code);

		return formDataMap;

	}
}
