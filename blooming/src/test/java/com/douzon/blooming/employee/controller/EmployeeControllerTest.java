package com.douzon.blooming.employee.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.employee.dto.request.LoginEmployeeDto;
import com.douzon.blooming.employee.dto.request.UpdateEmployeeDto;
import com.douzon.blooming.employee.service.EmployeeService;
import com.douzon.blooming.restdocs.RestDocsConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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

//@Disabled
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@Import(RestDocsConfig.class)
class EmployeeControllerTest {

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
  void login() throws Exception {
    LoginEmployeeDto loginEmployeeDto = new LoginEmployeeDto("member", "member");

    mockMvc.perform(post("/employees/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginEmployeeDto)))
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            requestFields(
                fieldWithPath("id").type(JsonFieldType.STRING).description("아이디")
                    .attributes(field("constraints", "NOT NULL")),
                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                    .attributes(field("constraints", "NOT NULL"))
            ),
            responseFields(
                fieldWithPath("grantType").type(JsonFieldType.STRING)
                    .description("승인 타입 : Bearer 고정"),
                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("토큰")
            )
        )).andReturn();
  }

  @Test
  void getEmployeeByNo() throws Exception {
    mockMvc.perform(get("/employees/{employeeNo}", 200001)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            pathParameters(
                parameterWithName("employeeNo").description("사원 번호")
            ),
            responseFields(
                fieldWithPath("employeeNo").type(JsonFieldType.NUMBER).description("사원 번호"),
                fieldWithPath("id").type(JsonFieldType.STRING).description("아이디"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                fieldWithPath("tel").type(JsonFieldType.STRING).description("전화번호"),
                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
            )
        )).andReturn();
  }

  @Test
  void findEmployeeList() throws Exception {
    mockMvc.perform(get("/employees/list")
            .contentType(MediaType.APPLICATION_JSON)
            .param("role", "ROLE_ADMIN")
            .param("employeeNo", String.valueOf(20))
            .param("name", "admin")
            .param("page", String.valueOf(1))
            .param("pageSize", String.valueOf(10))
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            requestParameters(
                parameterWithName("role").description("사원 역할"),
                parameterWithName("employeeNo").description("사원 번호"),
                parameterWithName("name").description("사원 이름"),
                parameterWithName("page").description("현재 페이지 번호"),
                parameterWithName("pageSize").description("페이지 크기")
            ),

            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            responseFields(
                fieldWithPath("list").type(JsonFieldType.ARRAY).description("사원 목록"),
                fieldWithPath("list[].employeeNo").type(JsonFieldType.NUMBER)
                    .description("사원 번호"),
                fieldWithPath("list[].name").type(JsonFieldType.STRING).description("이름"),
                fieldWithPath("list[].role").type(JsonFieldType.STRING).description("권한"),
                fieldWithPath("list[].tel").type(JsonFieldType.STRING).description("전화번호"),
                fieldWithPath("list[].email").type(JsonFieldType.STRING).description("이메일"),
                fieldWithPath("currentPage").type(JsonFieldType.NUMBER).description("현재 페이지"),
                fieldWithPath("hasNextPage").type(JsonFieldType.BOOLEAN)
                    .description("다음 페이지 존재 여부"),
                fieldWithPath("hasPreviousPage").type(JsonFieldType.BOOLEAN)
                    .description("이전 페이지 존재 여부")
            )
        )).andReturn();
  }

  @Test
  @Transactional
  void updateEmployee() throws Exception {
    UpdateEmployeeDto updateEmployeeDto = new UpdateEmployeeDto("admin", "newAdmin",
        "01045965429", "admin@admin.com");
    mockMvc.perform(put("/employees/{employeeNo}", 200001)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateEmployeeDto))
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
        .andExpect(status().isNoContent())
        .andDo(restDocs.document(
            pathParameters(
                parameterWithName("employeeNo").description("사원 번호")
            ),
            requestFields(
                fieldWithPath("oldPassword").type(JsonFieldType.STRING).description("기존 비밀번호"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("새로운 비밀번호"),
                fieldWithPath("tel").type(JsonFieldType.STRING).description("전화번호"),
                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
            ),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            )
        )).andReturn();
  }

  @Test
  void getImage() throws Exception {
    mockMvc.perform(get("/employees/{employeeNo}/image", 200001)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            pathParameters(
                parameterWithName("employeeNo").description("사원 번호")
            ),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            )
        )).andReturn();
  }

  @Test
  @Transactional
  void addImage() throws Exception {
    MockMultipartFile file = new MockMultipartFile("file", "test-image.jpg", "image/jpeg",
        "YourFileContent".getBytes());

    mockMvc.perform(multipart("/employees/{employeeNo}/image", 200001)
            .file(file)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
        .andExpect(status().isNoContent())
        .andDo(restDocs.document(
            pathParameters(
                parameterWithName("employeeNo").description("사원 번호")
            ),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            )
        )).andReturn();
  }

//  @Test
//  void modifyImage() throws Exception {
//    MockMultipartFile file = new MockMultipartFile("file", "test-image.jpg", "image/jpeg",
//        "YourFileContent".getBytes());
//
//    mockMvc.perform(multipart("/employees/{employeeNo}/image", 200001)
//            .file(file)
//            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
//        .andExpect(status().isNoContent())
//        .andDo(restDocs.document(
//            pathParameters(
//                parameterWithName("employeeNo").description("사원 번호")
//            ),
//            requestHeaders(
//                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
//            )
//        )).andReturn();
//  }

  @Test
  @Transactional
  void deleteImage() throws Exception {
    mockMvc.perform(delete("/employees/{employeeNo}/image", 200001)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
        .andExpect(status().isNoContent())
        .andDo(restDocs.document(
            pathParameters(
                parameterWithName("employeeNo").description("사원 번호")
            ),
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            )
        )).andReturn();
  }
}
