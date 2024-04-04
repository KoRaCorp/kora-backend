package corp.kora.kakao.client;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import corp.kora.kakao.response.KakaoFetchMemberInfoResponse;

@Component
public class KakaoFetchMemberInfoClient {
	private static final String FETCH_MEMBER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

	// Todo Exception 처리 필요
	public KakaoFetchMemberInfoResponse fetchMemberInfo(String accessToken) {
		return WebClient.create(FETCH_MEMBER_INFO_URL)
			.get()
			.headers(httpHeaders -> {
				httpHeaders.setBearerAuth(accessToken);
				httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			})
			.retrieve()
			.bodyToMono(KakaoFetchMemberInfoResponse.class)
			.block();
	}
}
