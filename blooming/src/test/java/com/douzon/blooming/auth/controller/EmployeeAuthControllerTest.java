package com.douzon.blooming.auth.controller;

import static com.douzon.blooming.restdocs.RestDocsConfig.field;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.douzon.blooming.auth.EmployeeRole;
import com.douzon.blooming.auth.dto.request.IdCheckDto;
import com.douzon.blooming.auth.dto.request.NoCheckDto;
import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.employee.dto.request.AuthUpdateEmployeeDto;
import com.douzon.blooming.employee.dto.request.InsertEmployeeDto;
import com.douzon.blooming.employee.dto.request.LoginEmployeeDto;
import com.douzon.blooming.employee.service.EmployeeService;
import com.douzon.blooming.restdocs.RestDocsConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

////@Disabled
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@Import(RestDocsConfig.class)
class EmployeeAuthControllerTest {

  private static final String BEARER_PREFIX = "Bearer ";
  private final ObjectMapper objectMapper = new ObjectMapper();
  @Autowired
  protected RestDocumentationResultHandler restDocs;
  @Autowired
  private EmployeeService employeeService;

  private MockMvc mockMvc;

  private TokenDto tokenDto;

  @BeforeEach
  public void setUp(WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation))
        .alwaysDo(MockMvcResultHandlers.print())
        .alwaysDo(restDocs)
        .addFilters(new CharacterEncodingFilter("UTF-8", true))
        .build();

    LoginEmployeeDto loginEmployeeDto = new LoginEmployeeDto("admin", "admin");
    tokenDto = employeeService.login(loginEmployeeDto);
  }

  @Test
  @Transactional
  void register() throws Exception {
    InsertEmployeeDto insertEmployeeDto = new InsertEmployeeDto(239999L, "tester", "tester",
        "tester",
        EmployeeRole.ROLE_MEMBER, "010-0000-0000", "test@test.com");

    mockMvc.perform(post("/auth/employees/register")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
            .content(objectMapper.writeValueAsString(insertEmployeeDto)))
        .andExpect(status().isNoContent())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            requestFields(
                fieldWithPath("employeeNo").type(JsonFieldType.NUMBER).description("사원 번호")
                    .attributes(field("constraints", "입사연도 (2) + 무작위 (4)")),
                fieldWithPath("name").type(JsonFieldType.STRING).description("이름")
                    .attributes(field("constraints", "NOT NULL")),
                fieldWithPath("id").type(JsonFieldType.STRING).description("아이디")
                    .attributes(field("constraints", "NOT NULL")),
                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                    .attributes(field("constraints", "NOT NULL")),
                fieldWithPath("role").type(JsonFieldType.STRING).description("권한"),
                fieldWithPath("tel").type(JsonFieldType.STRING).description("전화번호")
                    .attributes(field("constraints", "11 자리의 숫자")),
                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                    .attributes(field("constraints", "이메일 형식 XXX@XXX.XXX"))
            )
        )).andReturn();
  }

  @Test
  @Transactional
  void deleteEmployee() throws Exception {
    mockMvc.perform(delete("/auth/employees/{employeeNo}", 200001)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
        .andExpect(status().isNoContent())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            pathParameters(
                parameterWithName("employeeNo").description("사원 번호")
                    .attributes(field("constraints", "입사연도 (2) + 무작위 (4)"))
            )
        )).andReturn();
  }

  @Test
  @Transactional
  void updateEmployee() throws Exception {
    AuthUpdateEmployeeDto authUpdateEmployeeDto = new AuthUpdateEmployeeDto(200001L, "admin",
        "admin", "modify", EmployeeRole.ROLE_ADMIN, "01012345678", "test@test.com");
    mockMvc.perform(put("/auth/employees/{employeeNo}", 200001)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
            .content(objectMapper.writeValueAsString(authUpdateEmployeeDto)))
        .andExpect(status().isNoContent())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            pathParameters(
                parameterWithName("employeeNo").description("사원 번호")
                    .attributes(field("constraints", "입사연도 (2) + 무작위 (4)"))
            ),
            requestFields(
                fieldWithPath("employeeNo").type(JsonFieldType.NUMBER).description("사원 번호"),
                fieldWithPath("id").type(JsonFieldType.STRING).description("아이디")
                    .attributes(field("constraints", "입사연도 (2) + 무작위 (4)")),
                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                    .attributes(field("constraints", "NOT NULL")),
                fieldWithPath("name").type(JsonFieldType.STRING).description("이름")
                    .attributes(field("constraints", "NOT NULL")),
                fieldWithPath("role").type(JsonFieldType.STRING).description("권한"),
                fieldWithPath("tel").type(JsonFieldType.STRING).description("전화번호")
                    .attributes(field("constraints", "11 자리의 숫자")),
                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                    .attributes(field("constraints", "이메일 형식 XXX@XXX.XXX"))
            )
        )).andReturn();
  }

  @Test
  void noCheck() throws Exception {
    NoCheckDto noCheckDto = new NoCheckDto(200001L);
    mockMvc.perform(get("/auth/employees/no/check")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
            .content(objectMapper.writeValueAsString(noCheckDto)))
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            requestFields(
                fieldWithPath("employeeNo").description("사원 번호")
                    .attributes(field("constraints", "입사연도 (2) + 무작위 (4)"))
            ),
            responseFields(
                fieldWithPath("availability").description("사용 가능 여부")
            )
        )).andReturn();
  }

  @Test
  void idCheck() throws Exception {
    IdCheckDto idCheckDto = new IdCheckDto("admin");
    mockMvc.perform(get("/auth/employees/id/check")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
            .content(objectMapper.writeValueAsString(idCheckDto)))
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            requestFields(
                fieldWithPath("id").description("아이디")
                    .attributes(field("constraints", "NOT NULL"))
            ),
            responseFields(
                fieldWithPath("availability").description("사용 가능 여부")
            )
        )).andReturn();
  }
}