package com.apicatalog.base;

import java.util.function.Function;

/**
 * Base32 encoder and decoder based on RFC 4648.
 * <p>
 * Supports both standard Base32 and Base32hex variants, with optional padding.
 * </p>
 *
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc4648">RFC 4648</a>
 */
public class Base32 {

    /** RFC 4648 standard Base32 alphabet (uppercase). */
    public static final char[] ALPHABET_UPPER = new char[] {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2',
            '3', '4', '5', '6', '7',
    };

    /** RFC 4648 standard Base32 alphabet (lowercase). */
    public static final char[] ALPHABET_LOWER = new char[] {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '2',
            '3', '4', '5', '6', '7',
    };

    /** RFC 4648 Base32hex alphabet (lowercase). */
    public static final char[] ALPHABET_HEX_LOWER = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v',
    };

    /** RFC 4648 Base32hex alphabet (uppercase). */
    public static final char[] ALPHABET_HEX_UPPER = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V',
    };

    static final int[] PADDING = new int[] {
            6, 4, 3, 1, 0,
    };

    static final int[] PADDING_REVERSE = new int[] {
            0, 4, -1, 3, 2, -1, 1, -1,
    };

    /**
     * Encodes the given byte array into a Base32 string using the specified
     * alphabet.
     *
     * @param data     the data to encode
     * @param alphabet the encoding alphabet (must be 32 characters)
     * @param padding  whether to include padding characters ('=') in the output
     * @return the encoded string
     * @throws IllegalArgumentException if {@code data} is null
     */
    public static String encode(final byte[] data, final char[] alphabet, final boolean padding) {
        if (data == null) {
            throw new IllegalArgumentException("Input data must not be null.");
        }
        if (data.length == 0) {
            return "";
        }

        final StringBuilder encoded = new StringBuilder(((data.length / 5) + (data.length % 5 > 0 ? 1 : 0)) * 8);

        int rest = -1;

        for (int index = 0; index < data.length; index++) {
            switch (index % 5) {
            case 0:
                encoded.append(alphabet[(0x1f & (data[index] >>> 3))]);
                rest = (0x07 & data[index]) << 2;
                break;

            case 1:
                encoded.append(alphabet[rest | 0x1f & (data[index] >>> 6)]);
                encoded.append(alphabet[(0x3f & data[index]) >>> 1]);
                rest = (0x01 & data[index]) << 4;
                break;

            case 2:
                encoded.append(alphabet[rest | 0x1f & (data[index] >>> 4)]);
                rest = (0x0f & data[index]) << 1;
                break;

            case 3:
                encoded.append(alphabet[rest | 0x1f & (data[index] >>> 7)]);
                encoded.append(alphabet[(0x7f & data[index]) >>> 2]);
                rest = (0x03 & data[index]) << 3;
                break;

            case 4:
                encoded.append(alphabet[rest | 0x1f & (data[index] >>> 5)]);
                encoded.append(alphabet[0x1f & data[index]]);
                rest = -1;
                break;
            }
        }

        if (rest != -1) {
            encoded.append(alphabet[rest]);
        }

        // pads
        if (padding) {
            for (int index = 0; index < (PADDING[(data.length - 1) % 5]); index++) {
                encoded.append('=');
            }
        }

        return encoded.toString();
    }

    /**
     * Decodes a Base32 string into a byte array.
     *
     * @param encoded    the encoded string
     * @param charToCode a function that maps each character to its 5-bit code
     * @param padding    whether padding is expected in the input
     * @return the decoded byte array
     * @throws IllegalArgumentException if the input is null, malformed, or contains
     *                                  invalid characters
     */
    public static byte[] decode(final String encoded, final Function<Character, Integer> charToCode, boolean padding) {
        if (encoded == null) {
            throw new IllegalArgumentException("Encoded string must not be null.");
        }
        if (encoded.isEmpty()) {
            return new byte[0];
        }

        final char[] chars = encoded.toCharArray();

        final int trailing = chars.length % 8;

        final int pads;
        final int length;

        if (padding) {
            if (trailing > 0) {
                throw new IllegalArgumentException("Invalid padding: base32 string must be a multiple of 8 characters.");
            }
            pads = getPaddingLength(chars);
            length = chars.length - pads;

        } else {
            pads = trailing > 0 ? 8 - trailing : 0;
            length = chars.length;
        }

        final byte[] data = new byte[getDecodedLength(length, pads)];

        int decoded = 0;
        int rest = 0;

        for (int index = 0; index < length; index++) {

            int code = charToCode.apply(chars[index]);

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
                rest = 0;
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
                rest = 0;
                break;

            case 6:
                data[decoded] |= (byte) (0x03 & (code >>> 3));
                rest = (0x07 & code) << 5;
                decoded++;
                break;

            case 7:
                data[decoded] = (byte) (rest | code);
                rest = 0;
                decoded++;
                break;
            }
        }

        if (rest > 0) {
            throw new IllegalArgumentException("Invalid base32 string: leftover bits after decoding.");
        }

        return data;
    }

    static final int getDecodedLength(final int length, int pads) {

        final int total = (length / 8) * 5;

        final int diff = PADDING_REVERSE[pads];

        if (diff == -1) {
            throw new IllegalArgumentException("Invalid number of padding characters: " + pads);
        }

        return total + diff;
    }

    static final int getPaddingLength(final char[] data) {

        int pads = 0;

        for (int index = 1; index < 8; index++) {
            if (data[data.length - index] != '=') {
                return pads;
            }
            pads++;
        }

        return pads;
    }

    /**
     * Converts a Base32 character to its 5-bit code. Accepts both upper and
     * lowercase letters and digits '2' to '7'.
     *
     * @param ch the Base32 character
     * @return the 5-bit value
     * @throws IllegalArgumentException if the character is invalid
     */
    public static int charToCode(char ch) {
        if (ch >= 'a' && ch <= 'z') {
            return ch - (int) 'a';
        }
        if (ch >= 'A' && ch <= 'Z') {
            return ch - (int) 'A';
        }
        if (ch >= '2' && ch <= '7') {
            return ch - (int) '2' + 26;
        }
        throw new IllegalArgumentException("Invalid base32 character: '" + ch + "'");
    }

    /**
     * Converts a Base32hex character to its 5-bit code. Accepts lowercase letters
     * 'a' to 'v', uppercase 'A' to 'V', and digits '0' to '9'.
     *
     * @param ch the Base32hex character
     * @return the 5-bit value
     * @throws IllegalArgumentException if the character is invalid
     */
    public static int charToCodeHex(char ch) {
        if (ch >= 'a' && ch <= 'v') {
            return ch - (int) 'a' + 10;
        }
        if (ch >= 'A' && ch <= 'V') {
            return ch - (int) 'A' + 10;
        }
        if (ch >= '0' && ch <= '9') {
            return ch - (int) '0';
        }
        throw new IllegalArgumentException("Invalid base32hex character: '" + ch + "'");
    }
}
