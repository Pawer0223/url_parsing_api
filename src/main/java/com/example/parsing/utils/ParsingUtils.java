package com.example.parsing.utils;

import com.example.parsing.domain.ParsingDto;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class ParsingUtils {

    public static String removeTag(String body) {
        return body;
    }

    public static String getData(String url, int type) {
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        HttpClient httpClient = HttpClientBuilder.create()
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();
        factory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(factory);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String body = response.getBody();
        if (type == 1)
            return removeTag(body);
        return body;
    }

    public static ParsingDto getParsingDto(String data, int unit) {
        // make data
        int nmg = data.length() % unit;
        int substrIdx = data.length() - nmg;

        String content = data.substring(0, substrIdx);
        String remain = data.substring(substrIdx);

        return ParsingDto.builder()
                .content(content)
                .remain(remain)
                .build();
    }
}
