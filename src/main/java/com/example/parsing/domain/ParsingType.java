package com.example.parsing.domain;

import lombok.Getter;

@Getter
public enum ParsingType {
    REMOVE_HTML("REMOVE_HTML"),
    ALL_TEXT("ALL_TEXT");

    String value;
    ParsingType(String value) {
        this.value = value;
    }
}
