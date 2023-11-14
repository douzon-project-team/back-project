package com.douzon.blooming.delivery_instruction.controller;

import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.delivery_instruction.dto.request.DeleteDeliveryInstructionProductDto;
import com.douzon.blooming.delivery_instruction.dto.request.InsertDeliveryInstructionDto;
import com.douzon.blooming.delivery_instruction.dto.request.InsertDeliveryInstructionProductDto;
import com.douzon.blooming.delivery_instruction.dto.request.UpdateDeliveryInstructionProductDto;
import com.douzon.blooming.employee.dto.request.LoginEmployeeDto;
import com.douzon.blooming.employee.service.EmployeeService;
import com.douzon.blooming.product_instruction.dto.request.ProductInstructionDto;
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
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@Import(RestDocsConfig.class)
public class DeliveryInstructionControllerTest {

    private static final String BEARER_PREFIX = "Bearer";

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
    void addDeliveryInstructionTest() throws Exception {
        List<InsertDeliveryInstructionProductDto> productDtoList = new ArrayList<>();
        productDtoList.add(new InsertDeliveryInstructionProductDto(1L, 5));
        productDtoList.add(new InsertDeliveryInstructionProductDto(2L, 15));
        InsertDeliveryInstructionDto insertDeliveryInstructionDto =
                new InsertDeliveryInstructionDto("WO2311000002", productDtoList);

        mockMvc.perform(post("/delivery-instructions/{deliveryNo}", "MW2311000002")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
            .content(objectMapper.writeValueAsString(insertDeliveryInstructionDto)))
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
    void updateDeliveryInstructionTest() throws Exception {
        List<ProductInstructionDto> productDtoList = new ArrayList<>();
        productDtoList.add(new ProductInstructionDto(1L, 10, "updated"));
        productDtoList.add(new ProductInstructionDto(2L, 10, "updated"));
        productDtoList.add(new ProductInstructionDto(3L, 15, "added"));

        UpdateDeliveryInstructionProductDto dto = new UpdateDeliveryInstructionProductDto("WO2311000002", productDtoList);

        mockMvc.perform(put("/delivery-instructions/{deliveryNo}", "MW2311000002")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
            .content(objectMapper.writeValueAsString(dto)))
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
    void deleteDeliveryInstructionTest() throws Exception {
        DeleteDeliveryInstructionProductDto dto = new DeleteDeliveryInstructionProductDto("WO2311000002", 2L);
        mockMvc.perform(delete("/delivery-instructions/{deliveryNo}", "MW2311000002")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
            .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isNoContent())
            .andDo(restDocs.document(
                pathParameters(
                        parameterWithName("deliveryNo").description("출고 번호").attributes(field("constraints", "NOT NULL"))
                )
            ))
            .andReturn();
    }
}
