package com.example.parsing.utils;

import com.example.parsing.domain.ParsingDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ParsingUtils {
    private static final String TAG_REGX = "<(/)?([a-zA-Z0-9]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>";

    private static String getCrossString(List<Character> numberList, List<Character> alphabetList) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while (i < alphabetList.size()) {
            sb.append(alphabetList.get(i));
            if (i < numberList.size())
                sb.append(numberList.get(i));
            i++;
        }
        while (i < numberList.size()) {
            sb.append(numberList.get(i++));
        }
        return sb.toString();
    }

    public static ParsingDto divideUnit(String data, int unit) {
        int remainLen = data.length() % unit;
        int remainStartIdx = data.length() - remainLen;

        String content = data.substring(0, remainStartIdx);
        String remain = data.substring(remainStartIdx);

        return ParsingDto.builder()
                .content(content)
                .remain(remain)
                .build();
    }

    // 영어, 숫자, 오름차순, 교차 출력
    public static String parsing(String data, int type) {
        if (type == 1)
            data = data.replaceAll(TAG_REGX, "");
        List<Character> numberList = new ArrayList<>();
        List<Character> alphabetList = new ArrayList<>();
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            if (Character.isDigit(c))
                numberList.add(c);
            else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
                alphabetList.add(c);
        }
        Collections.sort(numberList);
        Collections.sort(alphabetList, (o1, o2) -> {
            char c1 = Character.toUpperCase(o1);
            char c2 = Character.toUpperCase(o2);
            int result = c1 - c2;
            if (result == 0) {
                // o1을 기준으로 정렬, o1이 대문자면 음수(우선), o1이 소문자면 양수(나중)
                return o1 - o2;
            }
            return result;
        });
        return getCrossString(numberList, alphabetList);
    }

    public static String getUrlData(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        HttpClient httpClient = HttpClientBuilder.create()
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();
        factory.setHttpClient(httpClient);
        restTemplate.setRequestFactory(factory);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        log.debug("result content type : {}", response.getHeaders().getContentType().toString());
        return response.getBody();
    }
}
