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

import corp.kora.api.bucket.application.BucketDeleteProcessor;
import corp.kora.api.bucket.presentation.response.BucketDeleteResponse;
import corp.kora.support.ControllerTestSupport;

class BucketDeleteControllerTest extends ControllerTestSupport {
	@DisplayName("회원이 버킷을 삭제한다.")
	@Test
	void delete() throws Exception {
		// given
		Long bucketId = 1L;
		doReturn(String.valueOf(bucketId))
			.when(tokenProvider)
			.extractPayload(anyString());

		doReturn(BucketDeleteResponse.from(bucketId))
			.when(bucketDeleteProcessor)
			.execute(any(BucketDeleteProcessor.Command.class));

		// when
		ResultActions perform = mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/buckets/{bucketId}/delete", bucketId)
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
					.summary("Bucket 삭제")
					.description("Bucket 삭제")
					.pathParameters(
						parameterWithName("bucketId").description("버킷 아이디")
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
