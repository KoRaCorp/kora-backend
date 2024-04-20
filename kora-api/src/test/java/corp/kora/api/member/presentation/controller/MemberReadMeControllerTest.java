package corp.kora.api.member.presentation.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import com.epages.restdocs.apispec.FieldDescriptors;
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;

import corp.kora.api.member.application.MemberReadMeManager;
import corp.kora.api.member.presentation.response.MemberReadMeResponse;
import corp.kora.member.domain.model.MemberReadModel;
import corp.kora.support.ControllerTestSupport;

class MemberReadMeControllerTest extends ControllerTestSupport {
	@DisplayName("회원 본인을 조회한다.")
	@Test
	void readMe() throws Exception {
		// given

		MemberReadModel memberReadModel = new MemberReadModel(1L, "example@gmail.com", "example", null, null);

		doReturn("1")
			.when(tokenProvider)
			.extractPayload(anyString());

		doReturn(MemberReadMeResponse.from(memberReadModel))
			.when(memberReadMeManager)
			.read(any(MemberReadMeManager.Query.class));

		// when

		ResultActions perform = mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/members/me")
					.contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", MOCK_AUTHORIZATION)
			)
			.andDo(print());
		// then
		expectOk(perform);

		// docs
		perform.andDo(
			MockMvcRestDocumentationWrapper.document(
				IDENTIFIER,
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag(TAG_MEMBER)
					.summary("회원 본인 조회")
					.description("회원 본인 조회.")
					.responseFields(
						new FieldDescriptors(objectDataResponseFields).and(
							fieldWithPath("data.memberId").type(JsonFieldType.NUMBER)
								.optional()
								.description("회원 아이디"),
							fieldWithPath("data.email").type(JsonFieldType.STRING)
								.description("이메일"),
							fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("닉네임"),
							fieldWithPath("data.profileImageUrl").type(JsonFieldType.STRING)
								.optional()
								.description("프로필 이미지 URL"),
							fieldWithPath("data.profileMessage").type(JsonFieldType.STRING)
								.optional()
								.description("프로필 메세지")
						)
					)
					.build())

			)
		);

	}
}
