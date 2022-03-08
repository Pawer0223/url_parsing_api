package com.example.parsing.service;

import com.example.parsing.domain.FormData;
import com.example.parsing.domain.ParsingDto;
import org.springframework.stereotype.Service;

import static com.example.parsing.utils.ParsingUtils.getData;
import static com.example.parsing.utils.ParsingUtils.getParsingDto;

@Service
public class ParsingService {
    public ParsingDto getUrlData(FormData formData) {
        String data = getData(formData.getUrl(), formData.getType());
        return getParsingDto(data, formData.getUnit());
    }
}
