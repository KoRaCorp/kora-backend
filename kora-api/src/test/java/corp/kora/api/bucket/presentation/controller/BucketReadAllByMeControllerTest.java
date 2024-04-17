package corp.kora.api.bucket.presentation.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import com.epages.restdocs.apispec.FieldDescriptors;
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;

import corp.kora.api.bucket.application.BucketReadAllByMeManager;
import corp.kora.api.bucket.presentation.response.BucketReadAllByMeResponse;
import corp.kora.bucket.domain.model.BucketReadModel;
import corp.kora.support.ControllerTestSupport;

class BucketReadAllByMeControllerTest extends ControllerTestSupport {
	@DisplayName("회원의 버킷들을 조회한다.")
	@Test
	void readAllByMe() throws Exception {
		// given
		Long bucketId = 1L;
		Long memberId = 1L;
		doReturn(String.valueOf(memberId))
			.when(tokenProvider)
			.extractPayload(anyString());

		BucketReadModel bucketReadModel = new BucketReadModel(bucketId, "bucketName", memberId, LocalDateTime.now(),
			LocalDateTime.now());

		doReturn(List.of(BucketReadAllByMeResponse.from(bucketReadModel)))
			.when(bucketReadAllByMeManager)
			.read(any(BucketReadAllByMeManager.Query.class));

		// when
		ResultActions perform = mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/buckets")
					.header("Authorization", MOCK_AUTHORIZATION)
					.contentType(MediaType.APPLICATION_JSON)
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
					.tag(TAG_BUCKET)
					.summary("자신의 버킷들을 조회한다.")
					.description("자신의 버킷들을 조회한다.")
					.responseFields(
						new FieldDescriptors(arrayDataResponseFields).and(
							fieldWithPath("data[].bucketId").type(JsonFieldType.NUMBER)
								.description("bucketId"),
							fieldWithPath("data[].bucketName").type(JsonFieldType.STRING)
								.description("bucketName")
						)

					)
					.build())
			)
		);

	}

}
