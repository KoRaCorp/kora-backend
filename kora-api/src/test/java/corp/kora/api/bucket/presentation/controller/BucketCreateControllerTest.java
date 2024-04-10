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

import corp.kora.api.bucket.application.BucketCreateProcessor;
import corp.kora.api.bucket.presentation.request.BucketCreateRequest;
import corp.kora.api.bucket.presentation.response.BucketCreateResponse;
import corp.kora.support.ControllerTestSupport;

class BucketCreateControllerTest extends ControllerTestSupport {

	@DisplayName("회원이 버킷을 생성한다.")
	@Test
	void create() throws Exception {
		// given
		// tokenProvider.extractPayload(authorization)
		doReturn("1")
			.when(tokenProvider)
			.extractPayload(anyString());

		doReturn(BucketCreateResponse.from(1L))
			.when(bucketCreateProcessor)
			.execute(any(BucketCreateProcessor.Command.class));

		// when
		ResultActions perform = mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/buckets/create")
					.header("Authorization", MOCK_AUTHORIZATION)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(new BucketCreateRequest("bucketName")))
			)
			.andDo(print());

		// then
		expectCreated(perform);

		// docs
		perform.andDo(
			MockMvcRestDocumentationWrapper.document(
				IDENTIFIER,
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag(TAG_BUCKET)
					.summary("Bucket 생성")
					.description("Bucket 생성")
					.requestFields(
						fieldWithPath("bucketName").type(JsonFieldType.STRING).description("생성할 bucket name")
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
