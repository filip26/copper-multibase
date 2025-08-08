package com.apicatalog.base;

/**
 * Base2 encoding and decoding (binary).
 * <p>
 * Each byte is represented by 8 bits (characters '0' or '1').
 */
public class Base2 {

    private Base2() {
        /* protected */}

    /**
     * Encodes the given byte array into a Base2 (binary) string.
     *
     * @param data the byte array to encode
     * @return a binary string representing the data
     * @throws IllegalArgumentException if {@code data} is {@code null}
     */
    public static String encode(final byte[] data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data must not be null.");
        }
        if (data.length == 0) {
            return "";
        }

        final StringBuilder encoded = new StringBuilder(data.length * 8);

        for (int index = 0; index < data.length; index++) {
            encoded.append(codeToChar(0x80 & data[index]))
                    .append(codeToChar(0x40 & data[index]))
                    .append(codeToChar(0x20 & data[index]))
                    .append(codeToChar(0x10 & data[index]))
                    .append(codeToChar(0x08 & data[index]))
                    .append(codeToChar(0x04 & data[index]))
                    .append(codeToChar(0x02 & data[index]))
                    .append(codeToChar(0x01 & data[index]));
        }
        return encoded.toString();
    }

    /**
     * Decodes a Base2-encoded string into a byte array.
     *
     * @param encoded the binary string to decode
     * @return the decoded byte array
     * @throws IllegalArgumentException if {@code encoded} is {@code null},
     *         has invalid length (not a multiple of 8), or contains characters other than '0' or '1'
     */
    public static byte[] decode(final String encoded) {
        if (encoded == null) {
            throw new IllegalArgumentException("Encoded string must not be null.");
        }
        if (encoded.isEmpty()) {
            return new byte[0];
        }

        final char[] chars = encoded.toCharArray();

        if (chars.length % 8 != 0) {
            throw new IllegalArgumentException("Encoded string length must be a multiple of 8.");
        }

        final byte[] data = new byte[chars.length / 8];

        for (int index = 0; index < data.length; index++) {
            data[index] = (byte) (charToCode(chars[index * 8]) << 7
                    | charToCode(chars[(index * 8) + 1]) << 6
                    | charToCode(chars[(index * 8) + 2]) << 5
                    | charToCode(chars[(index * 8) + 3]) << 4
                    | charToCode(chars[(index * 8) + 4]) << 3
                    | charToCode(chars[(index * 8) + 5]) << 2
                    | charToCode(chars[(index * 8) + 6]) << 1
                    | charToCode(chars[(index * 8) + 7]));
        }

        return data;
    }

    static char codeToChar(int code) {
        return code == 0 ? '0' : '1';
    }

    static int charToCode(char ch) {
        if (ch == '0') {
            return 0;
        }
        if (ch == '1') {
            return 1;
        }
        throw new IllegalArgumentException("Invalid character '" + ch + "'. Expected '0' or '1'.");
    }
}
