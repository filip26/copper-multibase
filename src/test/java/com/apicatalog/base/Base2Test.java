package com.apicatalog.base;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Base2Test {

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testData")
    void testEncode(String expected, byte[] data) {
        String output = Base2.encode(data);
        assertEquals(expected, output);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testData")
    void testDecode(String encoded, byte[] expected) {
        byte[] output = Base2.decode(encoded);
        assertArrayEquals(expected, output);
    }

    static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of("", "".getBytes()),
                Arguments.of("00000000", new byte[] { 0 }),
                Arguments.of("00000001", new byte[] { 1 }),
                Arguments.of("00000101", new byte[] { 5 }),
                Arguments.of("11111111", new byte[] { (byte) 255 }),
                Arguments.of("1000000011111110", new byte[] { (byte)128, (byte) 254 }));
    }
}
