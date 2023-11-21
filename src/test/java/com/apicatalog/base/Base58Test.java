package com.apicatalog.base;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Base58Test {

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testData")
    void testEncode(String expected, byte[] data) {
        String output = Base58.encode(data);
        assertEquals(expected, output);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testData")
    void testDecode(String encoded, byte[] expected) {
        byte[] output = Base58.decode(encoded);
        assertArrayEquals(expected, output);
    }

    static Stream<Arguments> testData() {
        return Stream.of(
          Arguments.of("2NEpo7TZRRrLZSi2U", "Hello World!".getBytes()),
          Arguments.of("USm3fpXnKG5EUBx2ndxBDMPVciP5hGey2Jh4NDv6gmeo1LkMeiKrLJUUBk6Z", "The quick brown fox jumps over the lazy dog.".getBytes())
        );
    }
}
