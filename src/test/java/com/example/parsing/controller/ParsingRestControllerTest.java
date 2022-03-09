package com.example.parsing.controller;

import com.example.parsing.domain.FormData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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

    @Test
    public void api_호출_잘못된_URL_호출() throws Exception {
        // given, when
        mockMvc.perform(post("/parsing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                //.param("url", "https://naver.com")
                .param("url", "https://aaa")
                .param("type", "1")
                .param("unit", "30"))
                .andDo(print())
                //then
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    public void api_호출_잘못된_URL() throws Exception {
        // given, when
        mockMvc.perform(post("/parsing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .param("url", "")
                .param("type", "1")
                .param("unit", "30"))
                .andDo(print())
                //then
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    public void api_호출_잘못된_type_입력() throws Exception {
        // given, when
        mockMvc.perform(post("/parsing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                //.param("url", "https://naver.com")
                .param("url", "https://naver.com")
                .param("type", "0")
                .param("unit", "100"))
                .andDo(print())
                //then
                .andExpect(status().isBadRequest())
        ;
        // given, when
        mockMvc.perform(post("/parsing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                //.param("url", "https://naver.com")
                .param("url", "https://naver.com")
                .param("type", "-1")
                .param("unit", "100"))
                .andDo(print())
                //then
                .andExpect(status().isBadRequest())
        ;
        // given, when
        mockMvc.perform(post("/parsing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                //.param("url", "https://naver.com")
                .param("url", "https://naver.com")
                .param("type", "1000000")
                .param("unit", "100"))
                .andDo(print())
                //then
                .andExpect(status().isBadRequest())
        ;
        // given, when
        mockMvc.perform(post("/parsing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                //.param("url", "https://naver.com")
                .param("url", "https://naver.com")
                .param("type", "aaa")
                .param("unit", "100"))
                .andDo(print())
                //then
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    public void api_호출_잘못된_unit_입력() throws Exception {
        // given, when
        mockMvc.perform(post("/parsing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                //.param("url", "https://naver.com")
                .param("url", "https://naver.com")
                .param("type", "1")
                .param("unit", "0"))
                .andDo(print())
                //then
                .andExpect(status().isBadRequest())
        ;
        // given, when
        mockMvc.perform(post("/parsing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                //.param("url", "https://naver.com")
                .param("url", "https://naver.com")
                .param("type", "1")
                .param("unit", "-1"))
                .andDo(print())
                //then
                .andExpect(status().isBadRequest())
        ;
        // given, when
        mockMvc.perform(post("/parsing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                //.param("url", "https://naver.com")
                .param("url", "https://naver.com")
                .param("type", "1")
                .param("unit", "a3"))
                .andDo(print())
                //then
                .andExpect(status().isBadRequest())
        ;
    }

}