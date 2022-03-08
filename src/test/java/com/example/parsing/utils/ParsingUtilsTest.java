package com.example.parsing.utils;

import com.example.parsing.domain.ParsingDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class ParsingUtilsTest {

    private static Object[] provideForTestParsing() {
        return new Object[]{
                new Object[]{"A0a1B2b3", 3, "A0a1B2", "b3"},
                new Object[]{"A0B1C2", 1, "A0B1C2", ""},
                new Object[]{"A0B1C2", 5, "A0B1C", "2"},
                new Object[]{"A0B1C2", 7, "", "A0B1C2"},
        };
    }

    @ParameterizedTest()
    @MethodSource("provideForTestParsing")
    public void parsing_basic_test(String data, int unit, String contentExpect, String remainExpect)
            throws Exception {
        //given: parameters
        //when
        ParsingDto parsingDto = ParsingUtils.getParsingDto(data, unit);
        //then
        Assertions.assertEquals(contentExpect, parsingDto.getContent());
        Assertions.assertEquals(remainExpect, parsingDto.getRemain());
    }

}