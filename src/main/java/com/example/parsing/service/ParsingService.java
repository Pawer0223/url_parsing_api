package com.example.parsing.service;

import com.example.parsing.domain.FormData;
import com.example.parsing.domain.ParsingDto;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ParsingService {

    private String getData(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        HttpClient httpClient = HttpClientBuilder.create()
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

        return ParsingDto.builder()
                .content(content)
                .remain(remain)
                .build();
    }

    public ParsingDto getUrlData(FormData formData) {
        String data = getData(formData.getUrl());
        return getParsingDto(formData.getUnit(), data);
    }
}
