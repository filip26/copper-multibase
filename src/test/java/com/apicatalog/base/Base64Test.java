package com.apicatalog.base;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Base64Test {

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testDataUPad")
    void testEncodeUpperPadding(String expected, byte[] data) {
        String output = Base32.encode(data, Base32.ALPHABET_UPPER, true);
        assertEquals(expected, output);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testDataUPad")
    void testDecodeUpperPadding(String encoded, byte[] expected) {
        byte[] output = Base32.decode(encoded, Base32::charToCode, true);
        assertArrayEquals(expected, output);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testDataU")
    void testEncodeUpper(String expected, byte[] data) {
        String output = Base32.encode(data, Base32.ALPHABET_UPPER, false);
        assertEquals(expected, output);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testDataU")
    void testDecodeUpper(String encoded, byte[] expected) {
        byte[] output = Base32.decode(encoded, Base32::charToCode, false);
        assertArrayEquals(expected, output);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testDataHexUPad")
    void testEncodeHexUpperPadding(String expected, byte[] data) {
        String output = Base32.encode(data, Base32.ALPHABET_HEX_UPPER, true);
        assertEquals(expected, output);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testDataHexUPad")
    void testDecodeHexUpperPadding(String encoded, byte[] expected) {
        byte[] output = Base32.decode(encoded, Base32::charToCodeHex, true);
        assertArrayEquals(expected, output);
    }

    static Stream<Arguments> testDataUPad() {
        return Stream.of(
                Arguments.of("", "".getBytes()),
                Arguments.of("AA======", new byte[] { 0 }),
                Arguments.of("AE======", new byte[] { 1 }),
                Arguments.of("MY======", "f".getBytes()),
                Arguments.of("MZXQ====", "fo".getBytes()),
                Arguments.of("MZXW6===", "foo".getBytes()),
                Arguments.of("MZXW6YQ=", "foob".getBytes()),
                Arguments.of("MZXW6YTB", "fooba".getBytes()),
                Arguments.of("MZXW6YTBOI======", "foobar".getBytes()),
                Arguments.of(
                        "KRUGS4ZAMRXWG5LNMVXHIIDEMVZWG4TJMJSXGIDUNBSSAY3PNVWW63TMPEQHK43FMQQGEYLTMUQDMNBMEBRGC43FEAZTELBAMFXGIIDCMFZWKIBRGYQGK3TDN5SGS3THEBZWG2DFNVSXGLQKJF2CAYLMONXSAZDJONRXK43TMVZSA5DIMUQHK43FEBXWMIDMNFXGKLLGMVSWI4ZANFXCAZLOMNXWIZLEEBSGC5DBFQQHK43FEBXWMIDQMFSGI2LOM4QGS3RAMVXGG33EMVSCAZDBORQSYIDVONSSA33GEBXG63RNMFWHA2DBMJSXIIDDNBQXEYLDORSXE4ZANFXCAZLOMNXWIZLEEBSGC5DBFQQHK43FEBXWMIDENFTGMZLSMVXHIIDFNZRW6ZDJNZTSAYLMOBUGCYTFORZSYIDBNZSCAY3BNZXW42LDMFWCAZLOMNXWI2LOM5ZS4===",
                        ("This document describes the commonly used base 64, base 32, and base 16 encoding schemes.\n"
                                + "It also discusses the use of line-feeds in encoded data, use of padding in encoded data, use of non-alphabet characters in encoded data, use of different encoding alphabets, and canonical encodings.")
                                .getBytes()));
    }

    static Stream<Arguments> testDataHexUPad() {
        return Stream.of(
                Arguments.of("", "".getBytes()),
                Arguments.of("00======", new byte[] { 0 }),
                Arguments.of("04======", new byte[] { 1 }),                
                Arguments.of("CO======", "f".getBytes()),
                Arguments.of("CPNG====", "fo".getBytes()),
                Arguments.of("CPNMU===", "foo".getBytes()),
                Arguments.of("CPNMUOG=", "foob".getBytes()),
                Arguments.of("CPNMUOJ1", "fooba".getBytes()),
                Arguments.of("CPNMUOJ1E8======", "foobar".getBytes()));
    }

    static Stream<Arguments> testDataU() {
        return Stream.of(
                Arguments.of("", "".getBytes()),
                Arguments.of("AA", new byte[] { 0 }),
                Arguments.of("AE", new byte[] { 1 }),
                Arguments.of("MY", "f".getBytes()),
                Arguments.of("MZXQ", "fo".getBytes()),
                Arguments.of("MZXW6", "foo".getBytes()),
                Arguments.of("MZXW6YQ", "foob".getBytes()),
                Arguments.of("MZXW6YTB", "fooba".getBytes()),
                Arguments.of("MZXW6YTBOI", "foobar".getBytes()),
                Arguments.of(
                        "KRUGS4ZAMRXWG5LNMVXHIIDEMVZWG4TJMJSXGIDUNBSSAY3PNVWW63TMPEQHK43FMQQGEYLTMUQDMNBMEBRGC43FEAZTELBAMFXGIIDCMFZWKIBRGYQGK3TDN5SGS3THEBZWG2DFNVSXGLQKJF2CAYLMONXSAZDJONRXK43TMVZSA5DIMUQHK43FEBXWMIDMNFXGKLLGMVSWI4ZANFXCAZLOMNXWIZLEEBSGC5DBFQQHK43FEBXWMIDQMFSGI2LOM4QGS3RAMVXGG33EMVSCAZDBORQSYIDVONSSA33GEBXG63RNMFWHA2DBMJSXIIDDNBQXEYLDORSXE4ZANFXCAZLOMNXWIZLEEBSGC5DBFQQHK43FEBXWMIDENFTGMZLSMVXHIIDFNZRW6ZDJNZTSAYLMOBUGCYTFORZSYIDBNZSCAY3BNZXW42LDMFWCAZLOMNXWI2LOM5ZS4",
                        ("This document describes the commonly used base 64, base 32, and base 16 encoding schemes.\n"
                                + "It also discusses the use of line-feeds in encoded data, use of padding in encoded data, use of non-alphabet characters in encoded data, use of different encoding alphabets, and canonical encodings.")
                                .getBytes()));
    }
}
