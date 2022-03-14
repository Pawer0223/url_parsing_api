package com.example.parsing.controller;

import com.example.parsing.domain.FormData;
import com.example.parsing.domain.ParsingDto;
import com.example.parsing.service.ParsingService;
import com.example.parsing.utils.ApiUtils.ApiResult;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.io.IOException;

import static com.example.parsing.utils.ApiUtils.success;

@Api(tags = {"Parsing"})
@RestController @RequiredArgsConstructor
public class ParsingRestController {

    private final ParsingService parsingService;

    @ApiOperation(value = "URL 호출 결과 Parsing", notes = "URL 호출 결과를 Parsing 하여 반환하는 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "요청 URL", required = true),
            @ApiImplicitParam(name = "type", value = "REMOVE_HTML = HTML 태그 제외, ALL_TEXT = TEXT 전체", required = true),
            @ApiImplicitParam(name = "unit", value = "결과 데이터를 나누는 단위", required = true)
    })
    @PostMapping(value = "/parsing",
                consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<ParsingDto> parsing_api(@Valid FormData formData) {
        System.out.println(formData.getType().getClass());
        return success(parsingService.parse(formData));
    }
}
