package com.douzon.blooming.token.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.restdocs.RestDocsConfig;
import com.douzon.blooming.token.dto.request.RequestReissueTokenDto;
import com.douzon.blooming.token.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@Import(RestDocsConfig.class)
class TokenControllerTest {

  private final ObjectMapper objectMapper = new ObjectMapper();
  @Autowired
  protected RestDocumentationResultHandler restDocs;

  @Autowired
  private TokenService tokenService;

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

    tokenDto = tokenService.createToken("admin", "1234", 200001L);
  }

  @Test
  void reissueToken() throws Exception {
    RequestReissueTokenDto dto = new RequestReissueTokenDto(
        tokenDto.getRefreshToken(), 200001L);
    mockMvc.perform(post("/token/reissue")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto))
        ).andExpect(status().isOk())
        .andDo(restDocs.document(
            responseFields(
                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("Access 토큰")
            )
        ))
        .andReturn();
  }
}
