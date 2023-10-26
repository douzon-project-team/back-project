package com.douzon.blooming.employee.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.douzon.blooming.employee.dto.request.EmployeeSearchDto;
import com.douzon.blooming.employee.dto.request.LoginDto;
import com.douzon.blooming.employee.dto.request.RequestEmployeeDto;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@Disabled
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@Import(RestDocsConfig.class)
public class EmployeeControllerTest {

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
    void login() throws Exception {
        LoginDto loginMember = new LoginDto("user2", "password2");
        mockMvc.perform(get("/employees/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginMember)))
                .andExpect(status().isOk())
                .andDo(restDocs.document(

                )).andReturn();
    }

    @Test
    void idDuplicateCheck() throws Exception {
        String id = "user21";
        mockMvc.perform(get("/employees/id-check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(id)))
                .andExpect(status().isOk())
                .andDo(restDocs.document(

                )).andReturn();
    }

    @Test
    void employeeNoDuplicateCheck() throws Exception {
        long employeeNo = 20L;
        mockMvc.perform(get("/employees/employee-no-check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Long.toString(employeeNo)))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                    requestBody()
                )).andReturn();
    }

    @Test
    void insertEmployee() throws Exception {
        RequestEmployeeDto dto = new RequestEmployeeDto(20, "user20", "password20", "jonson20","img" , 0L, "010-123-3422", "asmrl@aslrm.com");
        mockMvc.perform(post("/employees/insert")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andDo(restDocs.document()
                ).andReturn();
    }

    @Test
    void findEmployeeByNo() throws Exception {
        mockMvc.perform(get("/employees/{employeeNo}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("employeeNo").description("사번")
                        ),
                        responseFields(
                                fieldWithPath("employeeNo").type(JsonFieldType.NUMBER).description("사원 PK"),
                                fieldWithPath("id").type(JsonFieldType.STRING).description("아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("img").type(JsonFieldType.STRING).description("사진"),
                                fieldWithPath("tel").type(JsonFieldType.STRING).description("연락처"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                        )))
                .andReturn();
    }

    @Test
    void findEmployeeList() throws Exception {
        EmployeeSearchDto searchDto = new EmployeeSearchDto(null,"a",null);
//        EmployeeSearchDto searchDto = new EmployeeSearchDto();
        int page = 1;
        mockMvc.perform(get("/employees/list")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", String.valueOf(page))
                .content(objectMapper.writeValueAsString(searchDto)))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                responseFields(
                        subsectionWithPath("employeeList").description("사원 List"),
                        fieldWithPath("currentPage").type(JsonFieldType.NUMBER).description("현재 페이지"),
                        fieldWithPath("hasNextPage").type(JsonFieldType.BOOLEAN).description("다음 페이지 존재 여부"),
                        fieldWithPath("hasPreviousPage").type(JsonFieldType.BOOLEAN).description("이전 페이지 존재 여부")
                )));
    }

    @Test
    void updateEmployee() throws Exception {
        String password = "1234";
        mockMvc.perform(put("/employees/{employeeNo}/update-password", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(password))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("employeeNo").description("사번")
                        )
                )).andReturn();
    }

    @Test
    void deleteEmployee() throws Exception{
        mockMvc.perform(delete("/employees/{employeeNo}/delete-employee", 20L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("employeeNo").description("사번")
                        )
                )).andReturn();
    }
}
