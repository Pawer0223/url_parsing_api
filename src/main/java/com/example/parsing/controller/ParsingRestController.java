package com.example.parsing.controller;

import com.example.parsing.domain.FormData;
import com.example.parsing.domain.ParsingDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController @AllArgsConstructor
public class ParsingRestController {

    private String getData(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory();

        HttpClient httpClient =
                HttpClientBuilder.create()
                        .setRedirectStrategy(new LaxRedirectStrategy())
                        .build();

        factory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(factory);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    private ParsingDto getParsingDto(int unit, String data) {
        // make data
        int nmg = data.length() / unit;
        int substrIdx = data.length() - nmg;

        String content = data.substring(0, substrIdx);
        String remain = data.substring(substrIdx);

        ParsingDto parsingDto = ParsingDto.builder()
                .content(content)
                .remain(remain)
                .build();
        return parsingDto;
    }

    @PostMapping("/parsing")
    public ResponseEntity parsing_api(FormData formData) {
        log.info(formData.toString());

        // call
        formData.getUrl();
        String data = getData(formData.getUrl());

        ParsingDto parsingDto = getParsingDto(formData.getUnit(), data);
        return ResponseEntity.ok(parsingDto);
    }
}
