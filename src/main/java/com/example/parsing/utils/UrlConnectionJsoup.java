package com.example.parsing.utils;

import com.example.parsing.domain.ParsingType;
import com.example.parsing.errors.UrlConnectException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class UrlConnectionJsoup {

    String removeHtml(String data) {
        log.debug("html remove");
        return Jsoup.parse(data).text();
    }

    public String getUrlDataJsoup(String url, String type) {
        String errMsg = " invalid URL";
        try {
            Document doc = Jsoup.connect(url).get();
            String data = doc.toString();
            if (type.equals(ParsingType.REMOVE_HTML.getValue()))
                data = removeHtml(data);
            return data;
        } catch (IOException e) {
            throw new UrlConnectException(url + errMsg);
        } catch (IllegalArgumentException e) {
            throw new UrlConnectException(url + errMsg);
        }
    }

}
