package com.example.parsing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ParsingRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Test
    public void api_호출_기본_성공() throws Exception {
        // given, when
        mockMvc.perform(post("/parsing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                 .param("url", "https://naver.com")
                .param("type", "1")
                .param("unit", "30"))
        .andDo(print())
        //then
        .andExpect(status().isOk())
        .andExpect(jsonPath("success").exists())
        .andExpect(jsonPath("response").exists())
        ;
    }

    private static Object[] provideForTestWrongUrl() {
        return new Object[]{
                new Object[]{"https://aaa"},
                new Object[]{"http://aaa"},
                new Object[]{"aaa"},
                new Object[]{"https://nave1r.com"},
        };
    }
    @ParameterizedTest()
    @MethodSource("provideForTestWrongUrl")
    public void api_호출_잘못된_URL_호출(String url) throws Exception {

        String errMsg = " invalid URL";
        // given, when
        mockMvc.perform(post("/parsing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .param("url", url)
                .param("type", "1")
                .param("unit", "30"))
                //.andDo(print())
                //then
                .andExpect(jsonPath("error").exists())
                .andExpect(jsonPath("error.message").exists())
                .andExpect(jsonPath("error.message").value(url + errMsg))
                .andExpect(status().isBadRequest())
        ;
    }

    private static Object[] provideForTestWrongType() {
        return new Object[]{
                new Object[]{"0"},
                new Object[]{"-1"},
                new Object[]{"1000000"},
        };
    }
    @ParameterizedTest()
    @MethodSource("provideForTestWrongType")
    public void api_호출_잘못된_type_입력(String type) throws Exception {
        String errMsg = "Type 1(remove tag) or 2(all) only";
        // given, when
        mockMvc.perform(post("/parsing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .param("url", "https://naver.com")
                .param("type", type)
                .param("unit", "100"))
                .andExpect(jsonPath("error.message").exists())
                .andExpect(jsonPath("error.message").value(errMsg))
                // .andDo(print())
                //then
                .andExpect(status().isBadRequest())
        ;
    }

    private static Object[] provideForTestWrongUnit() {
        return new Object[]{
                new Object[]{"-1"},
                new Object[]{"0"},
                new Object[]{"200000000"},
                new Object[]{String.valueOf(Integer.MAX_VALUE)},
        };
    }
    @ParameterizedTest()
    @MethodSource("provideForTestWrongUnit")
    public void api_호출_잘못된_unit_입력(String unit) throws Exception {
        String errMsg = "Unit Please a number greater than 1 and less than 1000000";
        // given, when
        mockMvc.perform(post("/parsing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .param("url", "https://naver.com")
                .param("type", "1")
                .param("unit", unit))
                .andExpect(jsonPath("error.message").exists())
                .andExpect(jsonPath("error.message").value(errMsg))
                // .andDo(print())
                //then
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    public void api_호출_바인딩_에러() throws Exception {
        // given, when
        mockMvc.perform(post("/parsing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .param("url", "https://naver.com")
                .param("type", "abc")
                .param("unit", "10"))
                .andDo(print())
                //then
                .andExpect(status().isBadRequest())
        ;
        mockMvc.perform(post("/parsing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .param("url", "https://naver.com")
                .param("type", "1")
                .param("unit", "abc"))
                .andDo(print())
                //then
                .andExpect(status().isBadRequest())
        ;
        mockMvc.perform(post("/parsing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .param("url", "https://naver.com")
                .param("type", "abc")
                .param("unit",  String.valueOf(Long.MAX_VALUE)))
                .andDo(print())
                //then
                .andExpect(status().isBadRequest())
        ;
    }

    private static Object[] provideForTestController() {
        return new Object[]{
                new Object[]{"https://naver.com", "1", "100"},
                new Object[]{"https://google.com", "2", "30"},
                new Object[]{"https://okky.kr/articles/gathering", "1", "50"},
        };
    }

    @ParameterizedTest()
    @MethodSource("provideForTestController")
    public void 파싱_API_성공_테스트(String url, String type, String unit) throws Exception {
        // given, when
        mockMvc.perform(post("/parsing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .param("url", url)
                .param("type", type)
                .param("unit", unit))
                // .andDo(print())
                //then
                .andExpect(status().isOk())
        ;
    }

}