package corp.kora.kakao.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import corp.kora.kakao.request.KakaoGenerateTokenRequest;
import corp.kora.kakao.response.KakaoGenerateTokenResponse;

public class KakaoGenerateTokenClient {

	@Value("${oauth.kakao.client-id}")
	private String clientId;

	@Value("${oauth.kakao.redirect-uri}")
	private String redirectUri;

	private static final String GRANT_TYPE = "authorization_code";
	private static final String GENERATE_TOKEN_URL = "https://kauth.kakao.com/oauth/token";

	// Todo Exception 처리 필요
	public KakaoGenerateTokenResponse generateToken(String code) {
		KakaoGenerateTokenRequest request = new KakaoGenerateTokenRequest(
			clientId,
			GRANT_TYPE,
			redirectUri,
			code
		);

		return WebClient.create(GENERATE_TOKEN_URL)
			.post()
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.body(BodyInserters.fromFormData(request.toFormData()))
			.retrieve()
			.bodyToMono(KakaoGenerateTokenResponse.class)
			.block();
	}

}
