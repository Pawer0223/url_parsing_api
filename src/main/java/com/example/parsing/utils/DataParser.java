package com.example.parsing.utils;

import com.example.parsing.domain.ParsingDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class DataParser {

    private List<Character> numberList;
    private List<Character> alphabetList;

    ParsingDto divideUnit(String data, int unit) {
        int remainLen = data.length() % unit;
        int remainStartIdx = data.length() - remainLen;

        String content = data.substring(0, remainStartIdx);
        String remain = data.substring(remainStartIdx);

        return ParsingDto.builder()
                .content(content)
                .remain(remain)
                .build();
    }

    private String getCrossString() {
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

    private void sortList() {
        Collections.sort(numberList);
        Collections.sort(alphabetList, (o1, o2) -> {
            char c1 = Character.toUpperCase(o1);
            char c2 = Character.toUpperCase(o2);
            int result = c1 - c2;
            if (result == 0) {
                return o1 - o2;
            }
            return result;
        });
    }

    private void removeOtherCharacters(String data) {
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            if (Character.isDigit(c))
                numberList.add(c);
            else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
                alphabetList.add(c);
        }
    }

    // list는 parse가 호출될 때마다 새롭게 생성되어야 한다.
    public ParsingDto parse(String data, int unit) {
        numberList = new ArrayList<>();
        alphabetList = new ArrayList<>();

        removeOtherCharacters(data);
        sortList();
        String crossString = getCrossString();
        return divideUnit(crossString, unit);
    }
}
