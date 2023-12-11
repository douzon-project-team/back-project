package com.douzon.blooming.instruction.controller;


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
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.auth.filter.JwtFilter;
import com.douzon.blooming.instruction.dto.ProgressStatus;
import com.douzon.blooming.instruction.dto.TestAddDto;
import com.douzon.blooming.instruction.dto.TestUpdateDto;
import com.douzon.blooming.restdocs.RestDocsConfig;
import com.douzon.blooming.token.provider.TokenProvider;
import com.douzon.blooming.token.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
class InstructionControllerTest {

    private static final String BEARER_PREFIX = "Bearer ";
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    protected RestDocumentationResultHandler restDocs;
    private MockMvc mockMvc;
    @Autowired
    private TokenService tokenService;
    private TokenDto tokenDto;
    @Autowired
    private TokenProvider tokenProvider;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysDo(restDocs)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .addFilters(new JwtFilter(tokenProvider))
                .build();
        tokenDto = tokenService.createToken("admin", "1234", 200001L);
    }

    @Test
    void getInstructions() throws Exception {
        mockMvc.perform(get("/instructions/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("progressStatus", "PROGRESS")
                        .param("employeeName", "박상웅")
                        .param("startDate", "2019-10-03")
                        .param("endDate", "2024-10-10")
                        .param("expirationStartDate", "2021-10-10")
                        .param("expirationEndDate", "2024-10-10")
                        .param("page", "1")
                        .param("size", "8")
                        .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
                        ),
                        requestParameters(
                                parameterWithName("progressStatus").description("진행 상태"),
                                parameterWithName("employeeName").description("담당자"),
                                parameterWithName("startDate").description("지시 시작일"),
                                parameterWithName("endDate").description("지시 종료일"),
                                parameterWithName("expirationStartDate").description("지시 만료 시작일"),
                                parameterWithName("expirationEndDate").description("지시 만료 종료일"),
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지 크기")
                        ),
                        responseFields(
                                subsectionWithPath("list").description("지시 List"),
                                fieldWithPath("list[].instructionNo").type(JsonFieldType.STRING)
                                        .description("지시 번호"),
                                fieldWithPath("list[].employeeName").type(JsonFieldType.STRING)
                                        .description("사원 이름"),
                                fieldWithPath("list[].customerNo").type(JsonFieldType.NUMBER)
                                        .description("거래처 번호"),
                                fieldWithPath("list[].customerName").type(JsonFieldType.STRING)
                                        .description("거래처 이름"),
                                fieldWithPath("list[].instructionDate").type(JsonFieldType.STRING)
                                        .description("지시 일"),
                                fieldWithPath("list[].expirationDate").type(JsonFieldType.STRING)
                                        .description("마감 일"),
                                fieldWithPath("list[].progressStatus").type(JsonFieldType.STRING)
                                        .description("지시 상태"),
                                fieldWithPath("currentPage").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("hasNextPage").type(JsonFieldType.BOOLEAN)
                                        .description("다음 페이지 존재 여부"),
                                fieldWithPath("hasPreviousPage").type(JsonFieldType.BOOLEAN)
                                        .description("이전 페이지 존재 여부")
                        )))
                .andReturn();
    }

    @Test
    void getInstruction() throws Exception {
        mockMvc.perform(get("/instructions/{instructionNo}", "WO2312000001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("instructionNo").description("지시 번호")
                        ),
                        responseFields(
                                fieldWithPath("instructionNo").type(JsonFieldType.STRING).description("지시 번호"),
                                fieldWithPath("employeeName").type(JsonFieldType.STRING).description("담당자"),
                                fieldWithPath("customerName").type(JsonFieldType.STRING).description("거래처"),
                                fieldWithPath("customerNo").type(JsonFieldType.NUMBER).description("거래처 번호"),
                                subsectionWithPath("products").description("지시한 품목 List"),
                                fieldWithPath("instructionDate").type(JsonFieldType.STRING).description("지시일"),
                                fieldWithPath("expirationDate").type(JsonFieldType.STRING).description("완료일"),
                                fieldWithPath("progressStatus").type(JsonFieldType.STRING).description("진행 상태")
                        ))).andReturn();
    }

    @Test
    @Transactional
    void addInstruction() throws Exception {
        TestAddDto dto = new TestAddDto(200001L, 1L, "2023-11-24", "2023-11-24",
                ProgressStatus.STANDBY);
        mockMvc.perform(post("/instructions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
                        ),
                        requestFields(
                                fieldWithPath("employeeNo").type(JsonFieldType.NUMBER).description("지시 번호")
                                        .attributes(field("constraints", "NOT NULL")),
                                fieldWithPath("customerNo").type(JsonFieldType.NUMBER).description("거래처 번호")
                                        .attributes(field("constraints", "NOT NULL")),
                                fieldWithPath("instructionDate").type(JsonFieldType.STRING).description("지시일")
                                        .attributes(field("constraints", "YYYY-MM-DD")),
                                fieldWithPath("expirationDate").type(JsonFieldType.STRING).description("만료일")
                                        .attributes(field("constraints", "YYYY-MM-DD")),
                                fieldWithPath("progressStatus").type(JsonFieldType.STRING).description("지시 상태")
                                        .attributes(field("constraints", "NOT NULL"))
                        )
                ))
                .andReturn();
    }

    @Test
    @Transactional
    void updateInstruction() throws Exception {
        TestUpdateDto dto = new TestUpdateDto("WO2312000002", 1L, "2023-11-22", "2023-12-22");
        mockMvc.perform(put("/instructions/{instructionNo}", "WO2311000002")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("instructionNo").description("지시 번호")
                        ),
                        requestFields(
                                fieldWithPath("instructionNo").type(JsonFieldType.STRING).description("지시 번호")
                                        .attributes(field("constraints", "NOT NULL")),
                                fieldWithPath("customerNo").type(JsonFieldType.NUMBER).description("거래처 번호")
                                        .attributes(field("constraints", "NOT NULL")),
                                fieldWithPath("instructionDate").type(JsonFieldType.STRING).description("지시일")
                                        .attributes(field("constraints", "YYYY-MM-DD")),
                                fieldWithPath("expirationDate").type(JsonFieldType.STRING).description("만료일")
                                        .attributes(field("constraints", "YYYY-MM-DD"))
                        )
                )).andReturn();
    }

    @Test
    @Transactional
    void removeInstruction() throws Exception {
        mockMvc.perform(delete("/instructions/{instructionNo}", "WO2312000002")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
                .andExpect(status().isNoContent())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
                        ),
                        pathParameters(
                                parameterWithName("instructionNo").description("지시 번호")
                        )
                )).andReturn();
    }
}