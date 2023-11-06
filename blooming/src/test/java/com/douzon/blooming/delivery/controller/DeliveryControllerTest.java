package com.douzon.blooming.delivery.controller;

import com.douzon.blooming.auth.service.EmployeeAuthService;
import com.douzon.blooming.delivery.dto.DeliveryStatus;
import com.douzon.blooming.delivery.dto.request.InsertDeliveryDto;
import com.douzon.blooming.instruction.dto.ProgressStatus;
import com.douzon.blooming.instruction.dto.response.GetInstructionDto;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
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
    void addDelivery() throws Exception {
        List<ResponseProductInstructionDto> responseProductInstructionDtoList = new ArrayList<>();
        responseProductInstructionDtoList.add(new ResponseProductInstructionDto(1L, "VV0001", 10));
        responseProductInstructionDtoList.add(new ResponseProductInstructionDto(2L, "VV0002", 10));
        GetInstructionDto getInstructionDto = new GetInstructionDto("WO2310000001", "Sophia Garcia 15", "Douzone",
                responseProductInstructionDtoList, "2023-10-22", "2023-11-21", ProgressStatus.STANDBY);
        List<GetInstructionDto> getInstructionDtoList = new ArrayList<>();
        getInstructionDtoList.add(getInstructionDto);
        InsertDeliveryDto insertDeliveryDto = new InsertDeliveryDto(getInstructionDtoList, DeliveryStatus.INCOMPLETE, "2023-11-29");

        mockMvc.perform(post("/deliverys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(insertDeliveryDto)))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document(

                ))
                .andReturn();
    }
}