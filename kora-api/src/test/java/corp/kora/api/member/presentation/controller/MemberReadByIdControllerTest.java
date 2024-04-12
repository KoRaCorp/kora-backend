package corp.kora.api.member.presentation.controller;

import com.epages.restdocs.apispec.FieldDescriptors;
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import corp.kora.api.member.application.MemberReadByIdManager;
import corp.kora.api.member.presentation.response.MemberReadByIdResponse;
import corp.kora.member.domain.model.MemberReadModel;
import corp.kora.support.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberReadByIdControllerTest extends ControllerTestSupport {

    @DisplayName("회원 id로 회원을 조회한다.")
    @Test
    void readById() throws Exception {
        // given

        MemberReadModel memberReadModel = new MemberReadModel(1L, "example@gmail.com", "example", null, null);


        doReturn(MemberReadByIdResponse.from(memberReadModel))
                .when(memberFindByIdQueryManager)
                .read(any(MemberReadByIdManager.Query.class));

        // when

        ResultActions perform = mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/api/members/{memberId}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
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
                                .summary("회원 id로 회원을 조회")
                                .description("회원 id로 회원을 조회한다.")
                                .pathParameters(
                                        parameterWithName("memberId").description("회원 아이디")
                                )
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
