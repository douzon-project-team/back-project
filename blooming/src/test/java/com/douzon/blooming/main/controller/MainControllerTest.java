package com.douzon.blooming.main.controller;

import com.douzon.blooming.auth.dto.response.TokenDto;
import com.douzon.blooming.auth.filter.JwtFilter;
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
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@Disabled
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
@Import(RestDocsConfig.class)
@Slf4j
class MainControllerTest {

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
    void getMainPageData() throws Exception {
        mockMvc.perform(get("/main-page")
                .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
                        ),
                        responseFields(
                                subsectionWithPath("instruction").description("지시 금월,누적 count"),
                                subsectionWithPath("delivery").description("출고 금월,누적 count"),
                                subsectionWithPath("expirationDateNearInstruction").description("만료일이 가까운 지시"),
                                subsectionWithPath("customer").description("거래량이 많은 거래처")
                )))
                .andReturn();
    }

    @Test
    void getBarGraph() throws Exception {
        mockMvc.perform(get("/main-page/bar-graph/{type}", "month")
                        .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
                        ),
                        responseFields(
                                subsectionWithPath("instructionData").description("지시 (일,월,연)별 갯수 count"),
                                subsectionWithPath("deliveryData").description("출고 (일,월,연)별 갯수 count")
                        )))
                .andReturn();
    }

    @Test
    void getCircleGraph() throws Exception {
        mockMvc.perform(get("/main-page/circle-graph/{type}", "month")
                        .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokenDto.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("인증 토큰")
                        ),
                        responseFields(
                                subsectionWithPath("instructionData").description("지시 타입(일,월,연)별 진행 상태별 갯수 count"),
                                subsectionWithPath("deliveryData").description("출고 타입(일,월,연)별 진행 상태별 갯수 count")
                        )))
                .andReturn();
    }
}