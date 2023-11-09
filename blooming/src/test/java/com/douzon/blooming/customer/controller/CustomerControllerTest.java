package com.douzon.blooming.customer.controller;

import com.douzon.blooming.customer.dto.request.CustomerSearchDto;
import com.douzon.blooming.customer.dto.request.RequestCustomerDto;
import com.douzon.blooming.customer.dto.request.UpdateCustomerDto;
import com.douzon.blooming.restdocs.RestDocsConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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

@Disabled
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@Import(RestDocsConfig.class)
public class CustomerControllerTest {
    @Autowired
    protected RestDocumentationResultHandler restDocs;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysDo(restDocs)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @Transactional
    public void insertCustomer() throws Exception {
        RequestCustomerDto dto = new RequestCustomerDto("C0004", "Kakao", "010-7777-7777");
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document(

                ))
                .andReturn();
    }

    @Test
    public void getCustomer() throws Exception {
        mockMvc.perform(get("/customers/{customerNo}", 3L))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("customerNo").description("거래처 번호")
                        ),
                        responseFields(
                                fieldWithPath("customerNo").type(JsonFieldType.NUMBER).description("거래처 PK"),
                                fieldWithPath("customerCode").type(JsonFieldType.STRING).description("거래처 코드"),
                                fieldWithPath("customerName").type(JsonFieldType.STRING).description("거래처 명칭"),
                                fieldWithPath("customerTel").type(JsonFieldType.STRING).description("연락처")
                        )))
                .andReturn();
    }

    @Test
    public void getCustomers() throws Exception{
        mockMvc.perform(get("/customers/list")
                .contentType(MediaType.APPLICATION_JSON)
//                .param("customerName", "ka")
//                .param("page", "1")
//                .param("pageSize", "8")
                )
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                    responseFields(
                            subsectionWithPath("customerList").description("거래처 List"),
                            fieldWithPath("currentPage").type(JsonFieldType.NUMBER).description("현재 페이지"),
                            fieldWithPath("hasNextPage").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"),
                            fieldWithPath("hasPreviousPage").type(JsonFieldType.BOOLEAN).description("이전 페이지 존재 여부")
                    )))
                .andReturn();
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception{
        UpdateCustomerDto dto = new UpdateCustomerDto("kakao", "010-7736-6666");
        mockMvc.perform(put("/customers/{customerNo}", 3L)
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
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("customerNo").description("거래처 번호")
                        )
                ))
                .andReturn();
    }

}
