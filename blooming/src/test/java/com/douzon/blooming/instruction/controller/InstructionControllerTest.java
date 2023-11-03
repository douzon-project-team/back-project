package com.douzon.blooming.instruction.controller;


import com.douzon.blooming.auth.dto.request.LoginEmployeeDto;
import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.auth.service.EmployeeAuthService;
import com.douzon.blooming.instruction.dto.ProgressStatus;
import com.douzon.blooming.instruction.dto.request.RequestInstructionDto;
import com.douzon.blooming.instruction.dto.request.UpdateInstructionDto;
import com.douzon.blooming.product_instruction.dto.request.ProductInstructionDto;
import com.douzon.blooming.restdocs.RestDocsConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@Import(RestDocsConfig.class)
public class InstructionControllerTest {
    @Autowired
    protected RestDocumentationResultHandler restDocs;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    @Autowired
    private EmployeeAuthService employeeAuthService;
    private String accessToken;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysDo(restDocs)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
//        TokenDto login = employeeAuthService.login(new LoginEmployeeDto("admin", "admin"));
//        accessToken = "Bearer " + login.getAccessToken();
    }

    @Test
    @Transactional
    public void insertInstruction() throws Exception {
        List<ProductInstructionDto> productList = new ArrayList<>();
        productList.add(new ProductInstructionDto(2L, 25, null));
        productList.add(new ProductInstructionDto(3L, 10, null));

        RequestInstructionDto dto = new RequestInstructionDto(
                11L, 2L, productList, "2023-11-04", "2023-11-04", ProgressStatus.STANDBY
        );

        mockMvc.perform(post("/instructions")
//                        .header("Auth", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document(

                ))
                .andReturn();
    }

    @Test
    public void getInstruction() throws Exception {
        mockMvc.perform(get("/instructions/{instructionNo}", "WO2310000002")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("instructionNo").description("지시 번호")
                        ),
                        responseFields(
                                fieldWithPath("instructionNo").type(JsonFieldType.STRING).description("지시 PK"),
                                fieldWithPath("employeeName").type(JsonFieldType.STRING).description("담당자"),
                                fieldWithPath("customerName").type(JsonFieldType.STRING).description("거래처"),
                                subsectionWithPath("products").description("지시한 품목 List"),
                                fieldWithPath("instructionDate").type(JsonFieldType.STRING).description("지시일"),
                                fieldWithPath("expirationDate").type(JsonFieldType.STRING).description("완료일"),
                                fieldWithPath("progressStatus").type(JsonFieldType.STRING).description("진행 상태")
                        ))).andReturn();
    }

    @Test
    public void getInstructions() throws Exception {
//        SearchDto dto = new SearchDto(1L, "jonson", "2023-11-24", "2023-11-24", 1, 8);
        mockMvc.perform(get("/instructions/list")
                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("progressStatus", "1")
//                        .param("employeeName", "jonson")
                        .param("startDate", "2023-10-21")
                        .param("endDate", "2023-10-30"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        responseFields(
                                subsectionWithPath("instructions").description("지시 List"),
                                fieldWithPath("currentPage").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("hasNextPage").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"),
                                fieldWithPath("hasPreviousPage").type(JsonFieldType.BOOLEAN).description("이전 페이지 존재 여부")
                        )))
                .andReturn();
    }

    @Test
    @Transactional
    public void updateInstruction() throws Exception {
        List<ProductInstructionDto> products = new ArrayList<>();
        products.add(new ProductInstructionDto(1L, 20, "updated"));
        products.add(new ProductInstructionDto(3L, 10, "added"));
        UpdateInstructionDto dto = new UpdateInstructionDto(
                3L, products, "2023-11-22", "2023-12-22");

        mockMvc.perform(put("/instructions/{instructionNo}", "WO2311000002")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("instructionNo").description("지시 번호")
                        )
                )).andReturn();
    }

    @Test
    @Transactional
    public void deleteInstruction() throws Exception {
        mockMvc.perform(delete("/instructions/{instructionNo}", "WO2311000002")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("instructionNo").description("지시 번호")
                        )
                )).andReturn();
    }
}
