package com.example.parsing.domain;

import lombok.*;

@Getter @Setter @Builder @ToString
@NoArgsConstructor @AllArgsConstructor
public class FormData {
    private String url;
    private int type;
    private int unit;
}
