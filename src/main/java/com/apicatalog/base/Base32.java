package com.apicatalog.base;

/*
 * https://datatracker.ietf.org/doc/html/rfc4648
 */
public class Base32 {

    static char[] ALPHABET = new char[] {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2',
            '3', '4', '5', '6', '7',
    };

    static int[] PADDING = new int[] {
            6, 4, 3, 1, 0,
    };

    static int[] PADDING_REVERSE = new int[] {
            0, 5 - 4, -1, 5 - 3, 5 - 2, -1, 5 - 1, -1,
    };

    public static String encode(final byte[] data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (data.length == 0) {
            return "";
        }

        final StringBuilder encoded = new StringBuilder(((data.length / 5) + (data.length % 5 > 0 ? 1 : 0)) * 8);

        int rest = -1;

        for (int index = 0; index < data.length; index++) {

            switch (index % 5) {
            case 0:
                encoded.append(ALPHABET[data[index] >>> 3]);
                rest = (0x07 & data[index]) << 2;
                break;

            case 1:
                encoded.append(ALPHABET[rest | (data[index] >>> 6)]);
                encoded.append(ALPHABET[(0x3f & data[index]) >>> 1]);
                rest = (0x01 & data[index]) << 4;
                break;

            case 2:
                encoded.append(ALPHABET[rest | (data[index] >>> 4)]);
                rest = (0x0f & data[index]) << 1;
                break;

            case 3:
                encoded.append(ALPHABET[rest | (data[index] >>> 7)]);
                encoded.append(ALPHABET[(0x7f & data[index]) >>> 2]);
                rest = (0x03 & data[index]) << 3;
                break;

            case 4:
                encoded.append(ALPHABET[rest | (data[index] >>> 5)]);
                encoded.append(ALPHABET[0x1f & data[index]]);
                rest = -1;
                break;
            }
        }

        if (rest != -1) {
            encoded.append(ALPHABET[rest]);
        }

        // pads
        for (int index = 0; index < (PADDING[(data.length - 1) % 5]); index++) {
            encoded.append('=');
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

        char[] chars = encoded.toCharArray();
        int decoded = 0;

        int padding = getPaddingLength(chars);

        final byte[] data = new byte[getDecodedLength(chars, padding)];

        int rest = 0;

        for (int index = 0; index < (chars.length - padding); index++) {

            int code = charToCode(chars[index]);

            switch (index % 8) {
            case 0:
                data[decoded] = (byte) (code << 3);
                break;

            case 1:
                data[decoded] |= (byte) (0x07 & (code >>> 2));
                rest = (0x03 & code) << 6;
                decoded++;
                break;

            case 2:
                data[decoded] = (byte) (rest | code << 1);
                break;

            case 3:
                data[decoded] |= (byte) (0x01 & (code >>> 4));
                rest = (0x0f & code) << 4;
                decoded++;
                break;

            case 4:
                data[decoded] = (byte) (rest | code >>> 1);
                rest = (0x01 & code) << 7;
                decoded++;
                break;

            case 5:
                data[decoded] = (byte) (rest | code << 2);
                break;

            case 6:
                data[decoded] |= (byte) (0x03 & (code >>> 3));
                rest = (0x07 & code) << 5;
                decoded++;
                break;

            case 7:
                data[decoded] = (byte) (rest | code);
                decoded++;
                break;
            }
        }

        return data;
    }

    static final int getDecodedLength(final char[] data, int pads) {

        final int total = (data.length / 8) * 5;

        final int diff = PADDING_REVERSE[pads];

        if (diff == -1) {
            throw new IllegalArgumentException("Unknown pad size '" + pads + "'");
        }

        return total - diff;
    }

    static final int getPaddingLength(final char[] data) {

        int pads = 0;

        for (int index = 1; index < 8; index++) {
            if (data[data.length - index] != '=') {
                break;
            }
            pads++;
        }

        return pads;
    }

    static int charToCode(char ch) {
//        System.out.println("CH " + ch);
        if (ch >= 'A' && ch <= 'Z') {
            return ch - (int) 'A';
        }
        if (ch >= '2' && ch <= '7') {
            return ch - (int) '2' + 26;
        }

        throw new IllegalArgumentException();
    }
}
