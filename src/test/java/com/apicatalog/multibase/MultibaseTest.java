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
    @MethodSource(value = { "test58Data", "testData" })
    void testEncode(String expected, byte[] data) {
        Multibase base = DECODER.getBase(expected).orElseThrow(IllegalArgumentException::new);
        String encoded = base.encode(data);
        assertEquals(expected, encoded);
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
                Arguments.of("F666F6F6261", "fooba".getBytes()),
                Arguments.of("VCPNMUOG", "foob".getBytes()),
                Arguments.of("bmzxw6ytboi", "foobar".getBytes()),
                Arguments.of("BMZXW6YTBOI", "foobar".getBytes()),
                Arguments.of("CMZXW6YTBOI======", "foobar".getBytes()),
                Arguments.of("mZg", "f".getBytes()),
                Arguments.of("MZg==", "f".getBytes()),
                Arguments.of("mZm8", "fo".getBytes()),
                Arguments.of("mZm9v", "foo".getBytes()),
                Arguments.of("mZm9vYg", "foob".getBytes()),
                Arguments.of("mZm9vYmE", "fooba".getBytes()),
                Arguments.of("mZm9vYmFy", "foobar".getBytes()),
                Arguments.of("UZm9vYmE=", "fooba".getBytes())
                );
    }
}
