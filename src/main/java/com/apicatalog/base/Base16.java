package com.apicatalog.base;

/*
 * https://datatracker.ietf.org/doc/html/rfc4648
 */
public class Base16 {

    public static final char[] ALPHABET_LOWER = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f',
    };

    public static final char[] ALPHABET_UPPER = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F',
    };

    /**
     * Encodes the given byte array into a hexadecimal string using the specified
     * alphabet.
     *
     * @param data     the byte array to encode
     * @param alphabet the 16-character alphabet (lowercase or uppercase)
     * @return the base16-encoded string
     * @throws IllegalArgumentException if {@code data} is {@code null}
     */
    public static String encode(final byte[] data, final char[] alphabet) {
        if (data == null) {
            throw new IllegalArgumentException("Input data must not be null.");
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

    /**
     * Decodes a base16-encoded string into a byte array.
     *
     * @param encoded the base16-encoded string
     * @return the decoded byte array
     * @throws IllegalArgumentException if input is {@code null}, has odd length, or
     *                                  contains non-hex characters
     */
    public static byte[] decode(final String encoded) {
        if (encoded == null) {
            throw new IllegalArgumentException("Encoded string must not be null.");
        }
        if (encoded.isEmpty()) {
            return new byte[0];
        }

        final char[] chars = encoded.toCharArray();

        if (chars.length % 2 != 0) {
            throw new IllegalArgumentException("Encoded string must have an even number of characters.");
        }

        final byte[] data = new byte[chars.length / 2];

        for (int index = 0; index < data.length; index++) {

            int a = charToCode(chars[index * 2]);
            int b = charToCode(chars[(index * 2) + 1]);

            data[index] = (byte) (a << 4 | b);
        }

        return data;
    }

    /**
     * Converts a hexadecimal character to its integer value.
     *
     * @param ch the character to convert
     * @return integer value of the hex character
     * @throws IllegalArgumentException if the character is not in [0-9a-fA-F]
     */
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
        throw new IllegalArgumentException("Invalid hexadecimal character: '" + ch + "'");
    }
}
