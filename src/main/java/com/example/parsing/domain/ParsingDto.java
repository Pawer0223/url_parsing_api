package com.example.parsing.domain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ApiModel(value = "변환 결과", description = "URL 결과를 파싱한 후, Unit단위로 자른 결과 데이터")
@AllArgsConstructor @Builder @ToString
@Getter
public class ParsingDto {
    @ApiModelProperty(value = "Unit 단위 데이터")
    String content;
    @ApiModelProperty(value = "나머지 데이터")
    String remain;
}
