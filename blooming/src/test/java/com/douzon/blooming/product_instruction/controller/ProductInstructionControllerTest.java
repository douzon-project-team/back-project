package com.douzon.blooming.product_instruction.controller;

import static com.douzon.blooming.restdocs.RestDocsConfig.field;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.product_instruction.dto.TestAddDto;
import com.douzon.blooming.restdocs.RestDocsConfig;
import com.douzon.blooming.token.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
class ProductInstructionControllerTest {


  private static final String BEARER_PREFIX = "Bearer ";
  private final ObjectMapper objectMapper = new ObjectMapper();
  @Autowired
  protected RestDocumentationResultHandler restDocs;
  private MockMvc mockMvc;
  @Autowired
  private TokenService tokenService;
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
    tokenDto = tokenService.getToken("admin", "1234", 200001L);
  }

  @Test
  @Transactional
  void addProductInstruction() throws Exception {
    TestAddDto testAddDto = new TestAddDto(5L, 10);
    mockMvc.perform(post("/product-instruction/instruction/{instructionNo}", "WO2311000001")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
            .content(objectMapper.writeValueAsString(testAddDto))
        ).andExpect(status().isNoContent())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            requestFields(
                fieldWithPath("productNo").type(JsonFieldType.NUMBER).description("상품 번호")
                    .attributes(field("constraints", "NOT NULL")),
                fieldWithPath("amount").type(JsonFieldType.NUMBER).description("갯수")
                    .attributes(field("constraints", "NOT NULL"))
            )
        )).andReturn();
  }

  @Test
  @Transactional
  void updateProductInstruction() throws Exception {
    mockMvc.perform(put("/product-instruction/instruction/{instructionNo}/productNo/{productNo}",
            "WO2311000001", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
            .content("{\"amount\":10}")
        ).andExpect(status().isNoContent())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            requestFields(
                fieldWithPath("amount").type(JsonFieldType.NUMBER).description("갯수")
                    .attributes(field("constraints", "NOT NULL"))
            )
        )).andReturn();
  }

  @Test
  @Transactional
  void deleteProductInstruction() throws Exception {
    mockMvc.perform(delete("/product-instruction/instruction/{instructionNo}/productNo/{productNo}",
            "WO2311000001", "1")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
        ).andExpect(status().isNoContent())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            )
        )).andReturn();
  }
}