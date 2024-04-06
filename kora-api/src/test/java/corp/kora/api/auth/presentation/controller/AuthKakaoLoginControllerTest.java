package corp.kora.api.auth.presentation.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import com.epages.restdocs.apispec.FieldDescriptors;
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;

import corp.kora.api.auth.presentation.response.AuthKakaoLoginResponse;
import corp.kora.auth.domain.service.output.AuthToken;
import corp.kora.support.ControllerTestSupport;

class AuthKakaoLoginControllerTest extends ControllerTestSupport {

	@DisplayName("Kakao로 로그인한다.")
	@Test
	void login() throws Exception {
		// given
		doReturn(
			AuthKakaoLoginResponse.from(new AuthToken(
				"eyJyZHMiOiJRRTVjTSIsImFsZyI6IkhTMjU2In0.eyJzdWIiOiIxIiwiaWF0IjoxNzEyMzkxNTkxLCJleHAiOjE3MTI0Mjc1OTF9.NkG8jQIW_dqJmiw90ozJMoiopybsWZgCYxfnCLkdxlE",
				"eyJyZHMiOiJyZDVaRiIsImFsZyI6IkhTMjU2In0.eyJzdWIiOiIxIiwiaWF0IjoxNzEyMzkxNTkxLCJleHAiOjE3MTM2MDE1OTF9.3wesTUq_z-d9QmJR29YIomd65jl_dD20YYcy8xPnFPc")))
			.when(authKakaoLoginProcessor)
			.execute(anyString());

		// when
		ResultActions perform = mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/auth/kakao/login")
					.contentType(MediaType.APPLICATION_JSON)
					.queryParam("code",
						"imLQu4c8KfwkLPh_JiQg7-KuK_GaTf3MQA_vH6hU7LzgLdYdplMmGi0kDFcKPXWcAAABjq60EXGYFzyUYZmfhQ")
			)
			.andDo(print());
		// then
		perform.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.code").value(200))
			.andExpect(jsonPath("$.status").value("OK"))
			.andExpect(jsonPath("$.message").value("OK"))
			.andExpect(jsonPath("$.data").isNotEmpty());

		// docs
		perform.andDo(
			MockMvcRestDocumentationWrapper.document(
				IDENTIFIER,
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag(TAG_MEMBER)
					.summary("kakao로 로그인")
					.description("kakao로 로그인")
					.queryParameters(
						parameterWithName("code").description("카카오 인증 코드")
					)
					.responseFields(
						new FieldDescriptors(objectDataResponseFields).and(
							fieldWithPath("data.accessToken").type(JsonFieldType.STRING)
								.optional()
								.description("access Token"),
							fieldWithPath("data.refreshToken").type(JsonFieldType.STRING)
								.description("refresh Token"))
					)
					.build())
			)
		);

	}
}
