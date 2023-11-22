package com.douzon.blooming.customer.controller;

import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.customer.dto.request.CustomerSearchDto;
import com.douzon.blooming.customer.dto.request.RequestCustomerDto;
import com.douzon.blooming.customer.dto.request.UpdateCustomerDto;
import com.douzon.blooming.restdocs.RestDocsConfig;
import com.douzon.blooming.token.service.TokenService;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@Disabled
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@Import(RestDocsConfig.class)
public class CustomerControllerTest {
    private static final String BEARER_PREFIX = "Bearer";
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
        tokenDto = tokenService.createToken("admin", "1234", 200001L);
    }

    @Test
    @Transactional
    public void insertCustomer() throws Exception {
        RequestCustomerDto dto = new RequestCustomerDto("C0019", "swTech", "010-7777-7777", "박상웅", "프로젝트노에");
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document(

                ))
                .andReturn();
    }

    @Test
    public void getCustomer() throws Exception {
        mockMvc.perform(get("/customers/{customerNo}", 3L)
                .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("customerNo").description("거래처 번호")
                        ),
                        responseFields(
                                fieldWithPath("customerNo").type(JsonFieldType.NUMBER).description("거래처 PK"),
                                fieldWithPath("customerCode").type(JsonFieldType.STRING).description("거래처 코드"),
                                fieldWithPath("customerName").type(JsonFieldType.STRING).description("거래처 명칭"),
                                fieldWithPath("customerTel").type(JsonFieldType.STRING).description("연락처"),
                                fieldWithPath("ceo").type(JsonFieldType.STRING).description("대표자"),
                                fieldWithPath("sector").type(JsonFieldType.STRING).description("업종")
                        )))
                .andReturn();
    }

    @Test
    public void getCustomers() throws Exception{
        mockMvc.perform(get("/customers/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
//                .param("customerName", "ka")
//                .param("page", "1")
//                .param("pageSize", "8")
                )
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                    responseFields(
                            subsectionWithPath("list").description("거래처 List"),
                            subsectionWithPath("list.[].customerNo").type(JsonFieldType.NUMBER).description("거래처 PK"),
                            subsectionWithPath("list.[].customerCode").type(JsonFieldType.STRING).description("거래처 코드"),
                            subsectionWithPath("list.[].customerName").type(JsonFieldType.STRING).description("거래처 명칭"),
                            subsectionWithPath("list.[].customerTel").type(JsonFieldType.STRING).description("연락처"),
                            subsectionWithPath("list.[].ceo").type(JsonFieldType.STRING).description("대표자"),
                            subsectionWithPath("list.[].sector").type(JsonFieldType.STRING).description("업종"),
                            fieldWithPath("currentPage").type(JsonFieldType.NUMBER).description("현재 페이지"),
                            fieldWithPath("hasNextPage").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"),
                            fieldWithPath("hasPreviousPage").type(JsonFieldType.BOOLEAN).description("이전 페이지 존재 여부")
                    )))
                .andReturn();
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception{
        UpdateCustomerDto dto = new UpdateCustomerDto("하나금융지종", "010-7736-6666", "누구여");
        mockMvc.perform(put("/customers/{customerNo}", 18L)
                .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                    pathParameters(
                            parameterWithName("customerNo").description("거래처 번호")
                    )
                ))
                .andReturn();
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        mockMvc.perform(delete("/customers/{customerNo}", 3L)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("customerNo").description("거래처 번호")
                        )
                ))
                .andReturn();
    }

}
