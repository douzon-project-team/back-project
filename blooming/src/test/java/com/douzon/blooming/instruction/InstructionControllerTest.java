package com.douzon.blooming.instruction;


import com.douzon.blooming.instruction.dto.ProductListDto;
import com.douzon.blooming.instruction.dto.request.SearchDto;
import com.douzon.blooming.instruction.dto.request.TestDto;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void insertInstruction() throws Exception {
        List<ProductListDto> productList = new ArrayList<>();
        productList.add(new ProductListDto("VV0001", 15));
        productList.add(new ProductListDto("VV0002", 30));


        TestDto dto = new TestDto(
                "Sophia Garcia 15", "Lg", productList,  "2023-10-25", "2023-11-25", 1L
        );

        mockMvc.perform(post("/instructions/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andDo(restDocs.document(

                ))
                .andReturn();
    }

    @Test
    public void getInstruction() throws Exception {
        mockMvc.perform(get("/instructions/{instructionNo}", "WO2310000001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(restDocs.document(

                )).andReturn();
    }

    @Test
    public void getInstructions() throws Exception {
//        SearchDto dto = new SearchDto(1L, "jonson", "2023-11-24", "2023-11-24", 1, 8);
        mockMvc.perform(get("/instructions/list")
                .contentType(MediaType.APPLICATION_JSON)
                        .param("progressStatus", "1")
                        .param("employeeName", "jonson")
                        .param("startDate", "2023-10-24")
                        .param("endDate", "2023-10-24"))
                .andExpect(status().isOk())
                .andDo(restDocs.document(

                )).andReturn();
    }
}
