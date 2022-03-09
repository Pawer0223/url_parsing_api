package com.example.parsing.service;

import com.example.parsing.domain.FormData;
import com.example.parsing.domain.ParsingDto;
import org.springframework.stereotype.Service;

import static com.example.parsing.utils.ParsingUtils.*;

@Service
public class ParsingService {

    public ParsingDto parse(FormData formData) {
        String data = getUrlData(formData.getUrl());
        data = parsing(data, formData.getType());
        return divideUnit(data, formData.getUnit());
    }
}
