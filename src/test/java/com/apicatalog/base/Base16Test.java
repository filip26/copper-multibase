package com.apicatalog.base;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Base16Test {

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testDataU")
    void testEncodeUpper(String expected, byte[] data) {
        String output = Base16.encode(data, Base16.ALPHABET_UPPER);
        assertEquals(expected, output);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testDataU")
    void testDecodeUpper(String encoded, byte[] expected) {
        byte[] output = Base16.decode(encoded);
        assertArrayEquals(expected, output);
    }

    static Stream<Arguments> testDataU() {
        return Stream.of(
                Arguments.of("", "".getBytes()),
                Arguments.of("00", new byte[] { 0 }),
                Arguments.of("01", new byte[] { 1 }),
                Arguments.of("0001020F", new byte[] { 0, 1, 2, 15 }),
                Arguments.of("66", "f".getBytes()),
                Arguments.of("666F", "fo".getBytes()),
                Arguments.of("666F6F", "foo".getBytes()),
                Arguments.of("666F6F62", "foob".getBytes()),
                Arguments.of("666F6F6261", "fooba".getBytes()),
                Arguments.of("666F6F626172", "foobar".getBytes()));
    }
}
