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
}
