package com.douzon.blooming.todo.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.auth.filter.JwtFilter;
import com.douzon.blooming.restdocs.RestDocsConfig;
import com.douzon.blooming.todo.dto.request.RequestTodoDto;
import com.douzon.blooming.token.provider.TokenProvider;
import com.douzon.blooming.token.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@Import(RestDocsConfig.class)
class TodoControllerTest {

  private static final String BEARER_PREFIX = "Bearer ";
  private final ObjectMapper objectMapper = new ObjectMapper();
  @Autowired
  protected RestDocumentationResultHandler restDocs;
  @Autowired
  private TokenService tokenService;

  private MockMvc mockMvc;

  private TokenDto tokenDto;

  @Autowired
  private TokenProvider tokenProvider;

  @BeforeEach
  public void setUp(WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation))
        .alwaysDo(MockMvcResultHandlers.print())
        .alwaysDo(restDocs)
        .addFilters(new CharacterEncodingFilter("UTF-8", true))
        .addFilters(new JwtFilter(tokenProvider))
        .build();
    tokenDto = tokenService.createToken("admin", "1234", 200001L);
  }

  @Test
  void getTodo() throws Exception {
    mockMvc.perform(get("/todo/{todoNo}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            pathParameters(
                parameterWithName("todoNo").description("todo 번호")
            ),
            responseFields(
                fieldWithPath("todoNo").type(JsonFieldType.NUMBER).description("todo 번호"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                fieldWithPath("checked").type(JsonFieldType.BOOLEAN).description("체크 여부")
            )
        )).andReturn();
  }

  @Test
  void getTodoList() throws Exception {
    mockMvc.perform(get("/todo")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            responseFields(
                fieldWithPath("[].todoNo").type(JsonFieldType.NUMBER).description("todo 번호"),
                fieldWithPath("[].content").type(JsonFieldType.STRING).description("내용"),
                fieldWithPath("[].checked").type(JsonFieldType.BOOLEAN).description("체크 여부")
            )
        )).andReturn();
  }

  @Test
  @Transactional
  void addTodo() throws Exception {
    RequestTodoDto dto = new RequestTodoDto("오늘 할일");
    mockMvc.perform(post("/todo")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isNoContent())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            )
        )).andReturn();
  }

  @Test
  @Transactional
  void deleteTodo() throws Exception {
    mockMvc.perform(delete("/todo/{todoNo}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
        .andExpect(status().isNoContent())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            pathParameters(
                parameterWithName("todoNo").description("todo 번호")
            )
        )).andReturn();
  }
}