package com.apicatalog.base;

public class Base2 {

    public static String encode(final byte[] data) {
        if (data == null) {
            throw new IllegalArgumentException();
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

    public static byte[] decode(final String encoded) {
        if (encoded == null) {
            throw new IllegalArgumentException();
        }
        if (encoded.isEmpty()) {
            return new byte[0];
        }

        final char[] chars = encoded.toCharArray();

        if (chars.length % 8 > 0) {
            throw new IllegalArgumentException();
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
        throw new IllegalArgumentException("Illegal character '" + ch + "'");
    }
}
