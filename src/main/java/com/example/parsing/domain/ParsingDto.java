package com.example.parsing.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor @Builder @ToString
@Getter
public class ParsingDto {
    String content;
    String remain;
}
