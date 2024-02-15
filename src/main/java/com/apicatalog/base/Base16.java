package com.apicatalog.base;

/*
 * https://datatracker.ietf.org/doc/html/rfc4648
 */
public class Base16 {

    public static char[] ALPHABET_LOWER = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f',
    };

    public static char[] ALPHABET_UPPER = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F',
    };

    public static String encode(final byte[] data, final char[] alphabet) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (data.length == 0) {
            return "";
        }

        final StringBuilder encoded = new StringBuilder(data.length * 2);

        for (int index = 0; index < data.length; index++) {
            encoded.append(alphabet[0x0f & (data[index] >>> 4)]);
            encoded.append(alphabet[0x0f & data[index]]);
        }

        return encoded.toString();
    }

    public static byte[] decode(final String encoded) {
        if (encoded == null) {
            throw new IllegalArgumentException();
        }
        if (encoded.isEmpty()) {
            return new byte[0];
        }

        final char[] chars = encoded.toCharArray();

        if (chars.length % 2 > 0) {
            throw new IllegalArgumentException();
        }

        final byte[] data = new byte[chars.length / 2];

        for (int index = 0; index < data.length; index++) {

            int a = charToCode(chars[index * 2]);
            int b = charToCode(chars[(index * 2) + 1]);

            data[index] = (byte) (a << 4 | b);
        }

        return data;
    }

    public static int charToCode(char ch) {
        if (ch >= 'a' && ch <= 'f') {
            return ch - (int) 'a' + 10;
        }
        if (ch >= 'A' && ch <= 'F') {
            return ch - (int) 'A' + 10;
        }
        if (ch >= '0' && ch <= '9') {
            return ch - (int) '0';
        }
        throw new IllegalArgumentException("Illegal character '" + ch + "'");
    }
}
