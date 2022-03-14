package com.example.parsing.domain;

import com.example.parsing.validation.EnumValid;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Getter @Setter @Builder @ToString
@NoArgsConstructor @AllArgsConstructor
public class FormData {
    @NotBlank(message = "Url must be exist")
    private String url;
    @Range(min = 1, max = 10000000, message = "Unit Please a number greater than 1 and less than 1000000")
    private Integer unit;
    @EnumValid(enumClass = ParsingType.class, message = "Invalid Parsing Type")
    private String type;
}
