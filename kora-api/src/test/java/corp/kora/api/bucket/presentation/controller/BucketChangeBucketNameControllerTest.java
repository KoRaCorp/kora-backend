package corp.kora.api.bucket.presentation.controller;

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

import corp.kora.api.bucket.application.BucketChangeBucketNameProcessor;
import corp.kora.api.bucket.presentation.request.BucketChangeBucketNameRequest;
import corp.kora.api.bucket.presentation.response.BucketChangeBucketNameResponse;
import corp.kora.support.ControllerTestSupport;

class BucketChangeBucketNameControllerTest extends ControllerTestSupport {

	@DisplayName("회원이 특정 버킷 이름을 수정한다")
	@Test
	void changeBucketName() throws Exception {
		// given
		Long bucketId = 1L;
		doReturn(String.valueOf(bucketId))
			.when(tokenProvider)
			.extractPayload(anyString());

		doReturn(BucketChangeBucketNameResponse.from(bucketId))
			.when(bucketChangeBucketNameProcessor)
			.execute(any(BucketChangeBucketNameProcessor.Command.class));

		// when
		ResultActions perform = mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/buckets/{bucketId}/change-bucket-name", bucketId)
					.header("Authorization", MOCK_AUTHORIZATION)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(new BucketChangeBucketNameRequest("newBucketName")))
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
					.summary("Bucket명 수정")
					.description("Bucket명 수정")
					.pathParameters(
						parameterWithName("bucketId").description("버킷 아이디")
					)
					.requestFields(
						fieldWithPath("bucketName").type(JsonFieldType.STRING)
							.description("수정할 버킷 이름")
					)
					.responseFields(
						new FieldDescriptors(objectDataResponseFields).and(
							fieldWithPath("data.bucketId").type(JsonFieldType.NUMBER)
								.optional()
								.description("bucketId"))
					)
					.build())
			)
		);

	}

}
