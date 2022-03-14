package com.example.parsing.service;

import com.example.parsing.domain.FormData;
import com.example.parsing.domain.ParsingDto;
import com.example.parsing.utils.DataParser;
import com.example.parsing.utils.UrlConnectionJsoup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParsingService {

    private final UrlConnectionJsoup urlConnectionJsoup;
    private final DataParser dataParser;

    public ParsingDto parse(FormData formData) {
        String data = urlConnectionJsoup.getUrlDataJsoup(formData.getUrl(), formData.getType());
        return dataParser.parse(data, formData.getUnit());
    }
}
