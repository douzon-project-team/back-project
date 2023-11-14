package com.douzon.blooming.delivery.controller;

import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.delivery.dto.DeliveryStatus;
import com.douzon.blooming.delivery.dto.RequestDeliveryTestDto;
import com.douzon.blooming.delivery.dto.request.RequestDeliveryDto;
import com.douzon.blooming.delivery.dto.request.UpdateDeliveryDto;
import com.douzon.blooming.delivery_instruction.dto.request.InsertDeliveryInstructionDto;
import com.douzon.blooming.delivery_instruction.dto.request.InsertDeliveryInstructionProductDto;
import com.douzon.blooming.employee.dto.request.LoginEmployeeDto;
import com.douzon.blooming.employee.service.EmployeeService;
import com.douzon.blooming.instruction.dto.ProgressStatus;
import com.douzon.blooming.product_instruction.dto.request.ProductInstructionDto;
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

import java.util.ArrayList;
import java.util.List;

import static com.douzon.blooming.restdocs.RestDocsConfig.field;
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
public class DeliveryControllerTest {

    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    @Autowired
    private EmployeeService employeeService;
    private TokenDto tokenDto;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysDo(restDocs)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
        tokenDto = employeeService.login(new LoginEmployeeDto("admin", "admin"));
    }

    @Test
    @Transactional
    void addDelivery() throws Exception {
        RequestDeliveryTestDto dto = new RequestDeliveryTestDto("2023-12-25");
        mockMvc.perform(post("/deliveries")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                    responseFields(
                            fieldWithPath("deliveryNo").type(JsonFieldType.STRING).description("출고 PK")
                    )
            ))
            .andReturn();
    }

    @Test
    void getDelivery() throws Exception{
        mockMvc.perform(get("/deliveries/{deliveryNo}", "MW2311000001")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                pathParameters(
                        parameterWithName("deliveryNo").description("출고 번호")
                ),
                responseFields(
                        fieldWithPath("deliveryNo").type(JsonFieldType.STRING).description("출고 PK"),
                        fieldWithPath("employeeName").type(JsonFieldType.STRING).description("출고 담당자"),
                        fieldWithPath("deliveryDate").type(JsonFieldType.STRING).description("출고일"),
                        subsectionWithPath("instructions").description("출고할 지시 List"),
                        subsectionWithPath("instructions.[].instructionNo").type(JsonFieldType.STRING).description("지시 PK"),
                        subsectionWithPath("instructions.[].employeeName").type(JsonFieldType.STRING).description("지시 담당자"),
                        subsectionWithPath("instructions.[].customerName").type(JsonFieldType.STRING).description("거래처"),
                        subsectionWithPath("instructions.[].instructionDate").type(JsonFieldType.STRING).description("지시일"),
                        subsectionWithPath("instructions.[].expirationDate").type(JsonFieldType.STRING).description("만료일"),
                        subsectionWithPath("instructions.[].productNo").type(JsonFieldType.NUMBER).description("품목 PK"),
                        subsectionWithPath("instructions.[].productCode").type(JsonFieldType.STRING).description("품목 코드"),
                        subsectionWithPath("instructions.[].productName").type(JsonFieldType.STRING).description("품목 명칭"),
                        subsectionWithPath("instructions.[].amount").type(JsonFieldType.NUMBER).description("출고할 수량")
                )))
            .andReturn();
    }

    @Test
    void getDeliveries() throws Exception {
        mockMvc.perform(get("/deliveries/list")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
            .param("deliveryStatus", String.valueOf(DeliveryStatus.INCOMPLETE))
//            .param("", String.valueOf(DeliveryStatus.INCOMPLETE))
            .param("startDate", "2023-10-10")
            .param("endDate", "2023-12-26"))
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                responseFields(
                        subsectionWithPath("deliveries").description("출고 List"),
                        subsectionWithPath("deliveries.[].deliveryNo").type(JsonFieldType.STRING).description("출고 PK"),
                        subsectionWithPath("deliveries.[].employeeName").type(JsonFieldType.STRING).description("담당자"),
                        subsectionWithPath("deliveries.[].deliveryDate").type(JsonFieldType.STRING).description("출고일"),
                        subsectionWithPath("deliveries.[].deliveryStatus").type(JsonFieldType.STRING).description("출고 상태"),
                        subsectionWithPath("deliveries.[].instructionCount").type(JsonFieldType.NUMBER).description("총 지시 수"),
                        fieldWithPath("currentPage").type(JsonFieldType.NUMBER).description("현재 페이지"),
                        fieldWithPath("hasNextPage").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"),
                        fieldWithPath("hasPreviousPage").type(JsonFieldType.BOOLEAN).description("이전 페이지 존재 여부")
                )
            ))
            .andReturn();
    }

    @Test
    @Transactional
    void updateDelivery() throws Exception {
        RequestDeliveryTestDto updateDeliveryDto = new RequestDeliveryTestDto("2023-11-30");
        mockMvc.perform(put("/deliveries/{deliveryNo}", "MW2311000001")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
            .content(objectMapper.writeValueAsString(updateDeliveryDto)))
            .andExpect(status().isNoContent())
            .andDo(restDocs.document(
                pathParameters(
                        parameterWithName("deliveryNo").description("출고 번호").attributes(field("constraints", "NOT NULL"))
                )
            ))
            .andReturn();
    }

    @Test
    @Transactional
    void deleteDelivery() throws Exception {
        mockMvc.perform(delete("/deliveries/{deliveryNo}", "MW2311000001")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document(
                    pathParameters(
                            parameterWithName("deliveryNo").description("출고 번호").attributes(field("constraints", "NOT NULL"))
                    )
                ))
                .andReturn();
    }
}