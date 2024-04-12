package corp.kora.api.auth.presentation.controller;

import com.epages.restdocs.apispec.FieldDescriptors;
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import corp.kora.api.auth.presentation.response.AuthLoginInDevResponse;
import corp.kora.auth.domain.model.AuthTokenModel;
import corp.kora.support.ControllerTestSupport;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthLoginInDevControllerTest extends ControllerTestSupport {

    @DisplayName("auth key를 통해서 로그인을 한다.")
    @Test
    void login() throws Exception {
        // given
        doReturn(
                AuthLoginInDevResponse.from(new AuthTokenModel(
                        "eyJyZHMiOiJRRTVjTSIsImFsZyI6IkhTMjU2In0.eyJzdWIiOiIxIiwiaWF0IjoxNzEyMzkxNTkxLCJleHAiOjE3MTI0Mjc1OTF9.NkG8jQIW_dqJmiw90ozJMoiopybsWZgCYxfnCLkdxlE",
                        "eyJyZHMiOiJyZDVaRiIsImFsZyI6IkhTMjU2In0.eyJzdWIiOiIxIiwiaWF0IjoxNzEyMzkxNTkxLCJleHAiOjE3MTM2MDE1OTF9.3wesTUq_z-d9QmJR29YIomd65jl_dD20YYcy8xPnFPc")))
                .when(authLoginInDevProcessor)
                .execute(anyString());

        // when
        ResultActions perform = mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/api/auth/login-in-dev")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(Map.of("authKey", RandomString.make())))
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
                                .summary("개발 환경에서 편의를 위한 로그인 API")
                                .description("개발 환경에서 편의를 위한 로그인 API")
                                .requestFields(
                                        fieldWithPath("authKey").type(JsonFieldType.STRING)
                                                .description("인증 키")
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
