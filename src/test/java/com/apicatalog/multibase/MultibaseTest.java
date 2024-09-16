package com.apicatalog.multibase;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MultibaseTest {

    final static MultibaseDecoder DECODER = MultibaseDecoder.getInstance();

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testData")
    void testDecoderDecode(String encoded, byte[] expected, Multibase base) {
        assertArrayEquals(expected, DECODER.decode(encoded));
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testData")
    void testDecoderGetBase(String encoded, byte[] expected, Multibase base) {
        assertEquals(base, DECODER.getBase(encoded).orElse(null));
        assertEquals(base, DECODER.getBase(encoded.charAt(0)).orElse(null));
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testData")
    void testDecode(String encoded, byte[] expected, Multibase base) {
        assertArrayEquals(expected, base.decode(encoded));
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testData")
    void testEncode(String encoded, byte[] data, Multibase base) {
        assertEquals(encoded, base.encode(data));
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testData")
    void testIsEncoded(String encoded, byte[] data, Multibase base) {
        assertTrue(base.isEncoded(encoded));
    }

    static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of("F666F6F6261", "fooba".getBytes(), Multibase.BASE_16_UPPER),
                Arguments.of("VCPNMUOG", "foob".getBytes(), Multibase.BASE_32_HEX_UPPER),
                Arguments.of("bmzxw6ytboi", "foobar".getBytes(), Multibase.BASE_32),
                Arguments.of("BMZXW6YTBOI", "foobar".getBytes(), Multibase.BASE_32_UPPER),
                Arguments.of("CMZXW6YTBOI======", "foobar".getBytes(), Multibase.BASE_32_PAD_UPPER),
                Arguments.of("mZg", "f".getBytes(), Multibase.BASE_64),
                Arguments.of("MZg==", "f".getBytes(), Multibase.BASE_64_PAD),
                Arguments.of("mZm8", "fo".getBytes(), Multibase.BASE_64),
                Arguments.of("mZm9v", "foo".getBytes(), Multibase.BASE_64),
                Arguments.of("mZm9vYg", "foob".getBytes(), Multibase.BASE_64),
                Arguments.of("mZm9vYmE", "fooba".getBytes(), Multibase.BASE_64),
                Arguments.of("mZm9vYmFy", "foobar".getBytes(), Multibase.BASE_64),
                Arguments.of("zUXE7GvtEk8XTXs1GF8HSGbVA9FCX9SEBPe", "Decentralize everything!!".getBytes(), Multibase.BASE_58_BTC),
                Arguments.of("zStV1DL6CwTryKyV", "hello world".getBytes(), Multibase.BASE_58_BTC),
                Arguments.of("z7paNL19xttacUY", "yes mani !".getBytes(), Multibase.BASE_58_BTC),
                Arguments.of("MTXVsdGliYXNlIGlzIGF3ZXNvbWUhIFxvLw==", "Multibase is awesome! \\o/".getBytes(), Multibase.BASE_64_PAD),
                Arguments.of("zYAjKoNbau5KiqmHPmSxYCvn66dA1vLmwbt", "Multibase is awesome! \\o/".getBytes(), Multibase.BASE_58_BTC),
                Arguments.of("BJV2WY5DJMJQXGZJANFZSAYLXMVZW63LFEEQFY3ZP", "Multibase is awesome! \\o/".getBytes(), Multibase.BASE_32_UPPER),
                Arguments.of("F4D756C74696261736520697320617765736F6D6521205C6F2F", "Multibase is awesome! \\o/".getBytes(), Multibase.BASE_16_UPPER),
                Arguments.of("UZm9vYmE=", "fooba".getBytes(), Multibase.BASE_64_URL_PAD));
    }
}
