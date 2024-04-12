package corp.kora.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import corp.kora.api.auth.application.AuthKakaoLoginProcessor;
import corp.kora.api.auth.application.AuthLoginInDevProcessor;
import corp.kora.api.auth.presentation.controller.AuthKakaoLoginController;
import corp.kora.api.auth.presentation.controller.AuthLoginInDevController;
import corp.kora.api.bucket.application.BucketCreateProcessor;
import corp.kora.api.bucket.presentation.controller.BucketCreateController;
import corp.kora.api.member.application.MemberFindByIdManager;
import corp.kora.api.member.presentation.controller.MemberFindByIdController;
import corp.kora.auth.domain.provider.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {
        MemberFindByIdController.class,
        AuthKakaoLoginController.class,
        AuthLoginInDevController.class,
        BucketCreateController.class
})
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ExtendWith(RestDocumentationExtension.class)
public abstract class ControllerTestSupport {
    private static final String BEARER_PREFIX = "Bearer ";
    protected static final String IDENTIFIER = "{class-name}/{method-name}";
    protected static final String TAG_MEMBER = "📌 member";
    protected static final String TAG_BUCKET = "📌 bucket";

    protected static final String MOCK_AUTHORIZATION = BEARER_PREFIX
            + "eyJyZHMiOiI4T0hmaiIsImFsZyI6IkhTMjU2In0.eyJzdWIiOiIyIiwiaWF0IjoxNzAyNTM5NTc5LCJleHAiOjE3MDI1NDMxNzl9.jsT8xBAmQCzqlclVG7BdjTFqdXv4HRfEwxS9B9Z7ILA";

    @Autowired
    protected WebApplicationContext webApplicationContext;
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected TokenProvider tokenProvider;

    @MockBean
    protected MemberFindByIdManager memberFindByIdQueryManager;

    @MockBean
    protected AuthKakaoLoginProcessor authKakaoLoginProcessor;

    @MockBean
    protected AuthLoginInDevProcessor authLoginInDevProcessor;

    @MockBean
    protected BucketCreateProcessor bucketCreateProcessor;

    protected FieldDescriptor[] objectDataResponseFields = {
            PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태코드"),
            PayloadDocumentation.fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태"),
            PayloadDocumentation.fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
            PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING)
                    .description("성공 시 status와 동일 / 실패 시 예외 메시지 "),
            PayloadDocumentation.fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터")
    };

    protected FieldDescriptor[] arrayDataResponseFields = {
            PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태코드"),
            PayloadDocumentation.fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태"),
            PayloadDocumentation.fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
            PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING)
                    .description("성공 시 status와 동일 / 실패 시 예외 메시지 "),
            PayloadDocumentation.fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터")
    };

    protected FieldDescriptor[] paginationDataResponseFields = {
            PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.NUMBER).description("HTTP 상태코드"),
            PayloadDocumentation.fieldWithPath("status").type(JsonFieldType.STRING).description("HTTP 상태"),
            PayloadDocumentation.fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부"),
            PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING)
                    .description("성공 시 status와 동일 / 실패 시 예외 메시지 "),
            PayloadDocumentation.fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
            PayloadDocumentation.fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"),
            PayloadDocumentation.fieldWithPath("data.totalCount").type(JsonFieldType.NUMBER).description("전체 카운트 수"),
            PayloadDocumentation.fieldWithPath("data.items").type(JsonFieldType.ARRAY).description("응답 데이터 목록"),
    };

    protected void expectCreated(ResultActions perform) throws Exception {
        perform.andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.message").value("CREATED"))
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @BeforeEach
    void setUp(final RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();

    }
}
