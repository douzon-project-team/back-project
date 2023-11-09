package com.douzon.blooming.product.controller;

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
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.employee.dto.request.LoginEmployeeDto;
import com.douzon.blooming.employee.service.EmployeeService;
import com.douzon.blooming.product.dto.request.InsertProductDto;
import com.douzon.blooming.product.dto.request.UpdateProductDto;
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

//@Disabled
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@Import(RestDocsConfig.class)
class ProductControllerTest {

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
  void getProduct() throws Exception {
    mockMvc.perform(get("/products/{productNo}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            pathParameters(
                parameterWithName("productNo").description("상품 번호")
                    .attributes(field("constraints", "NOT NULL"))
            ),
            responseFields(
                fieldWithPath("productNo").type(JsonFieldType.NUMBER).description("상품 PK"),
                fieldWithPath("productCode").type(JsonFieldType.STRING).description("상품 코드"),
                fieldWithPath("productName").type(JsonFieldType.STRING).description("명칭"),
                fieldWithPath("standard").type(JsonFieldType.STRING).description("규격"),
                fieldWithPath("unit").type(JsonFieldType.NUMBER).description("단위")
            )))
        .andReturn();
  }

  @Test
  void getProducts() throws Exception {
    mockMvc.perform(get("/products/list")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
            .param("productCode", "AP")
            .param("productName", "AirPods")
            .param("pageSize", "8")
            .param("page", "1"))
        .andExpect(status().isOk())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            requestParameters(
                parameterWithName("productCode").description("상품 코드"),
                parameterWithName("productName").description("상품 이름"),
                parameterWithName("pageSize").description("페이지 크기"),
                parameterWithName("page").description("페이지 번호")
            ),
            responseFields(
                fieldWithPath("list").type(JsonFieldType.ARRAY).description("상품 목록"),
                fieldWithPath("list[].productNo").type(JsonFieldType.NUMBER)
                    .description("상품 번호"),
                fieldWithPath("list[].productCode").type(JsonFieldType.STRING).description("상품 코드"),
                fieldWithPath("list[].productName").type(JsonFieldType.STRING).description("품명"),
                fieldWithPath("list[].unit").type(JsonFieldType.NUMBER).description("개수"),
                fieldWithPath("currentPage").type(JsonFieldType.NUMBER).description("현재 페이지"),
                fieldWithPath("hasNextPage").type(JsonFieldType.BOOLEAN)
                    .description("다음 페이지 존재 여부"),
                fieldWithPath("hasPreviousPage").type(JsonFieldType.BOOLEAN)
                    .description("이전 페이지 존재 여부")
            )
        ))
        .andReturn();
  }

  @Test
  @Transactional
  void addProduct() throws Exception {
    InsertProductDto insertProductDto =
        new InsertProductDto("TT0001", "Test Product", "standard : X", 10);

    mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
            .content(objectMapper.writeValueAsString(insertProductDto)))
        .andExpect(status().isNoContent())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            requestFields(
                fieldWithPath("productCode").type(JsonFieldType.STRING).description("상품 코드"),
                fieldWithPath("productName").type(JsonFieldType.STRING).description("명칭"),
                fieldWithPath("standard").type(JsonFieldType.STRING).description("규격"),
                fieldWithPath("unit").type(JsonFieldType.NUMBER).description("갯수")
            )
        )).andReturn();
  }

  @Test
  @Transactional
  void removeProduct() throws Exception {
    mockMvc.perform(delete("/products/{productNo}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
        ).andExpect(status().isNoContent())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            pathParameters(
                parameterWithName("productNo").description("상품 번호")
                    .attributes(field("constraints", "NOT NULL"))
            )
        ))
        .andReturn();
  }

  @Test
  @Transactional
  void updateProduct() throws Exception {
    UpdateProductDto updateProductDto = new UpdateProductDto(1L, "AP0001", "MacBook Air 13",
        "1000mm * 100mm",
        20);
    mockMvc.perform(put("/products/{productNo}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
            .content(objectMapper.writeValueAsString(updateProductDto))
        )
        .andExpect(status().isNoContent())
        .andDo(restDocs.document(
            requestHeaders(
                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
            ),
            pathParameters(
                parameterWithName("productNo").description("상품 번호")
                    .attributes(field("constraints", "NOT NULL"))
            ),
            requestFields(
                fieldWithPath("productNo").type(JsonFieldType.NUMBER).description("상품 번호"),
                fieldWithPath("productCode").type(JsonFieldType.STRING).description("상품 코드"),
                fieldWithPath("productName").type(JsonFieldType.STRING).description("명칭"),
                fieldWithPath("standard").type(JsonFieldType.STRING).description("규격"),
                fieldWithPath("unit").type(JsonFieldType.NUMBER).description("갯수")
            )
        ))
        .andReturn();
  }
}