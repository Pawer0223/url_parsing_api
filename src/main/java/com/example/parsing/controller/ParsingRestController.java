package com.example.parsing.controller;

import com.example.parsing.domain.FormData;
import com.example.parsing.domain.ParsingDto;
import com.example.parsing.service.ParsingService;
import com.example.parsing.utils.ApiUtils.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.parsing.utils.ApiUtils.success;

@Slf4j
@RestController @RequiredArgsConstructor
public class ParsingRestController {

    private final ParsingService parsingService;

    @PostMapping("/parsing")
    public ApiResult<ParsingDto> parsing_api(@Valid FormData formData) {
        return success(parsingService.parse(formData));
    }
}
