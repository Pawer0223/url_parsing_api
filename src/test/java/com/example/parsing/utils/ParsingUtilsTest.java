package com.example.parsing.utils;

import com.example.parsing.domain.ParsingDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@Slf4j
class ParsingUtilsTest {

    private static Object[] provideForTestParsing() {
        return new Object[]{
                new Object[]{"<div><h1>hi</h1></div>", 1, "hi"},
                new Object[]{"<div><h1>hi</h1></div>", 2, "d1d1hhhiiivv"},
                new Object[]{"<a href=\"https://www.nAver.com\">네이버로</a>", 1, ""},
                new Object[]{"<a href=\"https://www.nAver.com\">2네이3버로1</a>", 2, "A1a2a3ceefhhmnoprrsttvwww"},
                new Object[]{"<h1>&lt;h1&gt;hi&lt;&#47;h1&gt;</h1>", 1, "g1g1h4h7hilltttt"},
                new Object[]{"zyxwvutsrqponmlkjihgfedcba", 1, "abcdefghijklmnopqrstuvwxyz"},
                new Object[]{"9876543210", 2, "0123456789"},
                new Object[]{"ZYXWVUTSRQPONMLKJIHGFEDCBA", 1, "ABCDEFGHIJKLMNOPQRSTUVWXYZ"},
                new Object[]{"zyxZYX987", 1, "X7x8Y9yZz"},
                new Object[]{"7X8zYyxZ9", 2, "X7x8Y9yZz"},
        };
    }

    @ParameterizedTest()
    @MethodSource("provideForTestParsing")
    public void parsing_basic_test(String data, int type, String expect) { //given
        //when
        String actual = ParsingUtils.parsing(data, type);
//        log.debug("======= ======= ======= test ======= ======= =======");
//        log.debug("before : {}", data);
//        log.debug("after : {}", actual);
        log.debug(actual);
        //then
        Assertions.assertEquals(expect, actual);
    }

    private static Object[] provideForTestDivide() {
        return new Object[]{
                new Object[]{"A0a1B2b3", 3, "A0a1B2", "b3"},
                new Object[]{"A0B1C2", 1, "A0B1C2", ""},
                new Object[]{"A0B1C2", 5, "A0B1C", "2"},
                new Object[]{"A0B1C2", 7, "", "A0B1C2"},
                new Object[]{"", 1000000, "", ""},
                new Object[]{"hi", 2, "hi", ""},
                new Object[]{"d1d1hhhiiivv", 8, "d1d1hhhi", "iivv"},
                new Object[]{"A1a2a3ceefhhmnoprrsttvwww", 3, "A1a2a3ceefhhmnoprrsttvww", "w"},
                new Object[]{"g1g1h4h7hilltttt", 2, "g1g1h4h7hilltttt", ""},
                new Object[]{"abcdefghijklmnopqrstuvwxyz", 100000000, "", "abcdefghijklmnopqrstuvwxyz"},
                new Object[]{"0123456789", 4, "01234567", "89"},
                new Object[]{"ABCDEFGHIJKLMNOPQRSTUVWXYZ", 20, "ABCDEFGHIJKLMNOPQRST", "UVWXYZ"},
                new Object[]{"X7x8Y9yZz", 4, "X7x8Y9yZ", "z"},
                new Object[]{"X7x8Y9yZz", 3, "X7x8Y9yZz", ""},
                new Object[]{"ABC", 4, "", "ABC"},
                new Object[]{"ABC", 3, "ABC", ""},
                new Object[]{"ABC", 2, "AB", "C"},
                new Object[]{"ABC", 1, "ABC", ""},
        };
    }

    @ParameterizedTest()
    @MethodSource("provideForTestDivide")
    public void divide_basic_test(String data, int unit, String expectContent, String expectRemain) { //given
        //when
        ParsingDto parsingDto = ParsingUtils.divideUnit(data, unit);
        log.debug("======= ======= ======= test ======= ======= =======");
        log.debug("before : {}", data);
        log.debug("after : {}", parsingDto);
        //then
        Assertions.assertEquals(expectContent, parsingDto.getContent());
        Assertions.assertEquals(expectRemain, parsingDto.getRemain());
    }

}