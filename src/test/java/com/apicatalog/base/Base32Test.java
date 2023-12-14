package com.apicatalog.base;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Base32Test {

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testData")
    void testEncode(String expected, byte[] data) {
        String output = Base32.encode(data);
        assertEquals(expected, output);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testData")
    void testDecode(String encoded, byte[] expected) {
        byte[] output = Base32.decode(encoded);
        assertArrayEquals(expected, output);
    }

    static Stream<Arguments> testData() {
        return Stream.of(
          Arguments.of("", "".getBytes()),
          Arguments.of("MY======", "f".getBytes()),
          Arguments.of("MZXQ====", "fo".getBytes()),
          Arguments.of("MZXW6===", "foo".getBytes()),
          Arguments.of("MZXW6YQ=", "foob".getBytes()),
          Arguments.of("MZXW6YTB", "fooba".getBytes()),
          Arguments.of("MZXW6YTBOI======", "foobar".getBytes())
        );
    }
}
