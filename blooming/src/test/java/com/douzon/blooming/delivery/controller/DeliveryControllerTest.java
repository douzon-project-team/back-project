package com.douzon.blooming.delivery.controller;

import com.douzon.blooming.auth.service.EmployeeAuthService;
import com.douzon.blooming.delivery.dto.DeliveryStatus;
import com.douzon.blooming.delivery.dto.request.InsertDeliveryDto;
import com.douzon.blooming.delivery.dto.request.UpdateDeliveryDto;
import com.douzon.blooming.delivery_instruction.dto.request.UpdateInstructionProductDto;
import com.douzon.blooming.instruction.dto.ProgressStatus;
import com.douzon.blooming.instruction.dto.response.GetInstructionDto;
import com.douzon.blooming.product_instruction.dto.request.ProductInstructionDto;
import com.douzon.blooming.product_instruction.dto.response.ResponseProductInstructionDto;
import com.douzon.blooming.restdocs.RestDocsConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@Import(RestDocsConfig.class)
public class DeliveryControllerTest {
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
    void addDelivery() throws Exception {
        List<ResponseProductInstructionDto> responseProductInstructionDtoList = new ArrayList<>();
//        responseProductInstructionDtoList.add(new ResponseProductInstructionDto(1L, "VV0001", 5));
//        responseProductInstructionDtoList.add(new ResponseProductInstructionDto(2L, "VV0002", 20));
        GetInstructionDto getInstructionDto = new GetInstructionDto("WO2310000002", "Sophia Garcia 15", "Lg",
                responseProductInstructionDtoList, "2023-10-25", "2023-11-25", ProgressStatus.STANDBY);
        List<GetInstructionDto> getInstructionDtoList = new ArrayList<>();
        getInstructionDtoList.add(getInstructionDto);
        InsertDeliveryDto insertDeliveryDto = new InsertDeliveryDto(getInstructionDtoList, DeliveryStatus.INCOMPLETE, "2023-11-30");

        mockMvc.perform(post("/deliveries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(insertDeliveryDto)))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document(

                ))
                .andReturn();
    }

    @Test
    void getDelivery() throws Exception{
        mockMvc.perform(get("/deliveries/{deliveryNo}", "MW2311000001")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                pathParameters(
                        parameterWithName("deliveryNo").description("출고 번호")
                ),
                responseFields(
                        fieldWithPath("deliveryNo").type(JsonFieldType.STRING).description("출고 PK"),
                        fieldWithPath("employeeName").type(JsonFieldType.STRING).description("담당자"),
                        fieldWithPath("deliveryDate").type(JsonFieldType.STRING).description("출고일"),
                        subsectionWithPath("instructions").description("출고할 지시 List")
                )))
            .andReturn();
    }

    @Test
    void getDeliveryDetails() throws Exception {
        mockMvc.perform(get("/deliveries/{deliveryNo}/{instructionNo}", "MW2311000001", "WO2310000001")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                    pathParameters(
                            parameterWithName("deliveryNo").description("출고 번호"),
                            parameterWithName("instructionNo").description("지시 번호")
                    ),
                    responseFields(
                            fieldWithPath("[].productNo").description("품목 번호"),
                            fieldWithPath("[].productCode").description("품목 코드"),
                            fieldWithPath("[].productName").description("품목 이름"),
                            fieldWithPath("[].amount").description("출고 수량")
                    )
            ))
            .andReturn();
    }

    @Test
    void getDeliveries() throws Exception {
        mockMvc.perform(get("/deliveries/list")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(restDocs.document(
                responseFields(
                        subsectionWithPath("deliveries").description("출고 List"),
                        fieldWithPath("currentPage").type(JsonFieldType.NUMBER).description("현재 페이지"),
                        fieldWithPath("hasNextPage").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"),
                        fieldWithPath("hasPreviousPage").type(JsonFieldType.BOOLEAN).description("이전 페이지 존재 여부")
                )
            ))
            .andReturn();
    }

    @Test
    void updateDeliveries() throws Exception {
        List<ProductInstructionDto> productInstructionDto = new ArrayList<>();

        List<UpdateInstructionProductDto> updateInstructionProductDto = new ArrayList<>();
        UpdateDeliveryDto updateDeliveryDto = new UpdateDeliveryDto(updateInstructionProductDto, "2023-11-30");
        mockMvc.perform(put("/deliveries/{deliveryNo}", "MW2311000001")
                .contentType(MediaType.APPLICATION_JSON))
//                .content())
                .andExpect(status().isNoContent())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("deliveryNo").description("출고 번호")
                        )
                ))
                .andReturn();
    }

}