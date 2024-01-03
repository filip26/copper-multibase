package com.apicatalog.multibase;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MultibaseTest {

    final MultibaseDecoder DECODER = MultibaseDecoder.getInstance();

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("test58Data")
    void testBase58BTCEncode(String expected, byte[] data) {
        String output = Multibase.BASE_58_BTC.encode(data);
        assertEquals(expected, output);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource(value = { "test58Data", "testData" })
    void testDecode(String encoded, byte[] expected) {
        byte[] output = DECODER.decode(encoded);
        assertArrayEquals(expected, output);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("test58Data")
    void testIsBase58BTCEncoded(String encoded, byte[] data) {
        assertTrue(Multibase.BASE_58_BTC.isEncoded(encoded));
    }

    @Test
    void testIsNotBase58BTCEncoded() {
        assertFalse(Multibase.BASE_58_BTC.isEncoded("abc"));
    }

    static Stream<Arguments> test58Data() {
        return Stream.of(
                Arguments.of("zUXE7GvtEk8XTXs1GF8HSGbVA9FCX9SEBPe", "Decentralize everything!!".getBytes()),
                Arguments.of("zStV1DL6CwTryKyV", "hello world".getBytes()),
                Arguments.of("z7paNL19xttacUY", "yes mani !".getBytes()));
    }

    static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of("vCPNMUOG", "foob".getBytes()),
                Arguments.of("bMZXW6YTBOI", "foobar".getBytes()),
                Arguments.of("BMZXW6YTBOI", "foobar".getBytes()),
                Arguments.of("CMZXW6YTBOI======", "foobar".getBytes()));
    }
}
