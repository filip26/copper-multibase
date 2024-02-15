package com.apicatalog.multibase;

import java.util.Base64;
import java.util.Objects;
import java.util.function.Function;

import com.apicatalog.base.Base16;
import com.apicatalog.base.Base32;
import com.apicatalog.base.Base58;

/**
 * Represents multibase encoding.
 */
public class Multibase {

    public static final Multibase BASE_16 = new Multibase('f', 16,
            Base16::decode,
            d -> Base16.encode(d, Base16.ALPHABET_LOWER));

    public static final Multibase BASE_16_UPPER = new Multibase('F', 16,
            Base16::decode,
            d -> Base16.encode(d, Base16.ALPHABET_UPPER));

    public static final Multibase BASE_32_HEX = new Multibase('v', 32,
            e -> Base32.decode(e, Base32::charToCodeHex, false),
            d -> Base32.encode(d, Base32.ALPHABET_HEX_LOWER, false));

    public static final Multibase BASE_32_HEX_UPPER = new Multibase('V', 32,
            e -> Base32.decode(e, Base32::charToCodeHex, false),
            d -> Base32.encode(d, Base32.ALPHABET_HEX_UPPER, false));

    public static final Multibase BASE_32_HEX_PAD = new Multibase('t', 32,
            e -> Base32.decode(e, Base32::charToCodeHex, true),
            d -> Base32.encode(d, Base32.ALPHABET_LOWER, true));

    public static final Multibase BASE_32_HEX_PAD_UPPER = new Multibase('T', 32,
            e -> Base32.decode(e, Base32::charToCodeHex, true),
            d -> Base32.encode(d, Base32.ALPHABET_UPPER, true));

    public static final Multibase BASE_32 = new Multibase('b', 32,
            e -> Base32.decode(e, Base32::charToCode, false),
            d -> Base32.encode(d, Base32.ALPHABET_LOWER, false));

    public static final Multibase BASE_32_UPPER = new Multibase('B', 32,
            e -> Base32.decode(e, Base32::charToCode, false),
            d -> Base32.encode(d, Base32.ALPHABET_UPPER, false));

    public static final Multibase BASE_32_PAD = new Multibase('c', 32,
            e -> Base32.decode(e, Base32::charToCode, true),
            d -> Base32.encode(d, Base32.ALPHABET_LOWER, true));

    public static final Multibase BASE_32_PAD_UPPER = new Multibase('C', 32,
            e -> Base32.decode(e, Base32::charToCode, true),
            d -> Base32.encode(d, Base32.ALPHABET_UPPER, true));

    public static final Multibase BASE_64 = new Multibase('m', 64,
            Base64.getDecoder()::decode,
            Base64.getEncoder().withoutPadding()::encodeToString
            );

    public static final Multibase BASE_64_PAD = new Multibase('M', 64,
            Base64.getMimeDecoder()::decode,
            Base64.getMimeEncoder()::encodeToString
            );

    public static final Multibase BASE_64_URL = new Multibase('u', 64,
            Base64.getUrlDecoder()::decode,
            Base64.getUrlEncoder().withoutPadding()::encodeToString
            );

    public static final Multibase BASE_64_URL_PAD = new Multibase('U', 64,
            Base64.getUrlDecoder()::decode,
            Base64.getUrlEncoder()::encodeToString
            );

    public static final Multibase BASE_58_BTC = new Multibase('z', 58,
            Base58::decode,
            Base58::encode);

    protected final char prefix;
    protected final int length;

    protected final Function<String, byte[]> decode;
    protected final Function<byte[], String> encode;

    public Multibase(
            char prefix,
            int length,
            Function<String, byte[]> decode,
            Function<byte[], String> encode) {
        this.prefix = prefix;
        this.length = length;
        this.decode = decode;
        this.encode = encode;
    }

    /**
     * A unique prefix identifying base encoding in encoded value.
     * 
     * @return the base encoding unique prefix
     */
    public char prefix() {
        return prefix;
    }

    /**
     * An encoding alphabet length. e.g. 32, 58, 64.
     * 
     * @return the encoding alphabet length
     */
    public int length() {
        return length;
    }

    /**
     * Checks if the given value is encoded with the base.
     * 
     * @param encoded an encoded value to test
     * @return <code>true</code> is the given value is encoded with the base,
     *         <code>false</code> otherwise
     */
    public boolean isEncoded(final String encoded) {
        return encoded != null
                && encoded.trim().length() > 0
                && prefix == encoded.charAt(0);
    }

    /**
     * Decodes the given data into byte array.
     * 
     * @param encoded to decodes
     * @return encoded data as byte array
     */
    public byte[] decode(final String encoded) {
        if (encoded == null) {
            throw new IllegalArgumentException("The encoded value must not be null.");
        }

        if (encoded.trim().length() == 0) {
            throw new IllegalArgumentException("The encoded value be non empty string.");
        }

        final char p = encoded.charAt(0);

        if (prefix != p) {
            throw new IllegalArgumentException("Unsupported multibase encoding [" + p + "], this instance process only [" + prefix + "].");
        }

        final String data = encoded.substring(1);

        return decode.apply(data);
    }

    /**
     * Encodes the given data into base encoded string.
     * 
     * @param data to encode
     * @return a string representing the encoded data
     */
    public String encode(byte[] data) {

        if (data == null) {
            throw new IllegalArgumentException("The data must not be null.");
        }

        if (data.length == 0) {
            throw new IllegalArgumentException("The data must be non empty byte array.");
        }

        return prefix + encode.apply(data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prefix);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Multibase other = (Multibase) obj;
        return prefix == other.prefix;
    }
}
