package com.apicatalog.multibase;

import java.util.Base64;
import java.util.Objects;
import java.util.function.Function;

import com.apicatalog.base.Base16;
import com.apicatalog.base.Base2;
import com.apicatalog.base.Base32;
import com.apicatalog.base.Base58;

/**
 * Represents a multibase encoding format.
 * <p>
 * Multibase is a convention for identifying which base encoding has been used
 * to encode binary data by prefixing the encoded string with a unique
 * character. This allows for consistent and unambiguous decoding across
 * different base encodings, such as Base16, Base32, Base58, and Base64
 * variants.
 * </p>
 * <p>
 * Each {@code Multibase} instance includes:
 * <ul>
 * <li>A unique base name (e.g., {@code base64url})</li>
 * <li>A single-character prefix used to identify the base in encoded data</li>
 * <li>The alphabet size (e.g., 32, 58, 64)</li>
 * <li>Functions for encoding and decoding</li>
 * </ul>
 * </p>
 *
 * @see <a href="https://github.com/multiformats/multibase">Multibase
 *      specification</a>
 */
public class Multibase {

    public static final Multibase BASE_2 = new Multibase("base2", '0', 2,
            Base2::decode,
            Base2::encode);

    public static final Multibase BASE_16 = new Multibase("base16", 'f', 16,
            Base16::decode,
            d -> Base16.encode(d, Base16.ALPHABET_LOWER));

    public static final Multibase BASE_16_UPPER = new Multibase("base16upper", 'F', 16,
            Base16::decode,
            d -> Base16.encode(d, Base16.ALPHABET_UPPER));

    public static final Multibase BASE_32_HEX = new Multibase("base32hex", 'v', 32,
            e -> Base32.decode(e, Base32::charToCodeHex, false),
            d -> Base32.encode(d, Base32.ALPHABET_HEX_LOWER, false));

    public static final Multibase BASE_32_HEX_UPPER = new Multibase("base32hexupper", 'V', 32,
            e -> Base32.decode(e, Base32::charToCodeHex, false),
            d -> Base32.encode(d, Base32.ALPHABET_HEX_UPPER, false));

    public static final Multibase BASE_32_HEX_PAD = new Multibase("base32hexpad", 't', 32,
            e -> Base32.decode(e, Base32::charToCodeHex, true),
            d -> Base32.encode(d, Base32.ALPHABET_LOWER, true));

    public static final Multibase BASE_32_HEX_PAD_UPPER = new Multibase("base32hexpadupper", 'T', 32,
            e -> Base32.decode(e, Base32::charToCodeHex, true),
            d -> Base32.encode(d, Base32.ALPHABET_UPPER, true));

    public static final Multibase BASE_32 = new Multibase("base32", 'b', 32,
            e -> Base32.decode(e, Base32::charToCode, false),
            d -> Base32.encode(d, Base32.ALPHABET_LOWER, false));

    public static final Multibase BASE_32_UPPER = new Multibase("base32upper", 'B', 32,
            e -> Base32.decode(e, Base32::charToCode, false),
            d -> Base32.encode(d, Base32.ALPHABET_UPPER, false));

    public static final Multibase BASE_32_PAD = new Multibase("base32pad", 'c', 32,
            e -> Base32.decode(e, Base32::charToCode, true),
            d -> Base32.encode(d, Base32.ALPHABET_LOWER, true));

    public static final Multibase BASE_32_PAD_UPPER = new Multibase("base32padupper", 'C', 32,
            e -> Base32.decode(e, Base32::charToCode, true),
            d -> Base32.encode(d, Base32.ALPHABET_UPPER, true));

    public static final Multibase BASE_64 = new Multibase("base64", 'm', 64,
            Base64.getDecoder()::decode,
            Base64.getEncoder().withoutPadding()::encodeToString);

    public static final Multibase BASE_64_PAD = new Multibase("base64pad", 'M', 64,
            Base64.getMimeDecoder()::decode,
            Base64.getMimeEncoder()::encodeToString);

    public static final Multibase BASE_64_URL = new Multibase("base64url", 'u', 64,
            Base64.getUrlDecoder()::decode,
            Base64.getUrlEncoder().withoutPadding()::encodeToString);

    public static final Multibase BASE_64_URL_PAD = new Multibase("base64urlpad", 'U', 64,
            Base64.getUrlDecoder()::decode,
            Base64.getUrlEncoder()::encodeToString);

    public static final Multibase BASE_58_BTC = new Multibase("base58btc", 'z', 58,
            Base58::decode,
            Base58::encode);

    protected static final Multibase[] ALL = new Multibase[] {
            Multibase.BASE_58_BTC,
            Multibase.BASE_64,
            Multibase.BASE_64_PAD,
            Multibase.BASE_64_URL,
            Multibase.BASE_64_URL_PAD,
            Multibase.BASE_32,
            Multibase.BASE_32_UPPER,
            Multibase.BASE_32_PAD,
            Multibase.BASE_32_PAD_UPPER,
            Multibase.BASE_32_HEX,
            Multibase.BASE_32_HEX_UPPER,
            Multibase.BASE_32_HEX_PAD,
            Multibase.BASE_32_HEX_PAD_UPPER,
            Multibase.BASE_16,
            Multibase.BASE_16_UPPER,
            Multibase.BASE_2
    };

    protected final String name;

    protected final char prefix;
    protected final int length;

    protected final Function<String, byte[]> decode;
    protected final Function<byte[], String> encode;

    /**
     * Constructs a {@code Multibase} instance.
     *
     * @param name   the unique base name (e.g., "base64urlpad")
     * @param prefix the unique prefix character indicating the base
     * @param length the base alphabet length
     * @param decode the decoding function
     * @param encode the encoding function
     */
    public Multibase(
            String name,
            char prefix,
            int length,
            Function<String, byte[]> decode,
            Function<byte[], String> encode) {
        this.prefix = prefix;
        this.length = length;
        this.decode = decode;
        this.encode = encode;
        this.name = name;
    }

    @Deprecated
    public Multibase(
            char prefix,
            int length,
            Function<String, byte[]> decode,
            Function<byte[], String> encode) {
        this.prefix = prefix;
        this.length = length;
        this.decode = decode;
        this.encode = encode;
        this.name = "" + prefix;
    }

    /**
     * Returns the unique base name of this multibase (e.g., {@code base64url}).
     *
     * @return the multibase name
     */
    public String name() {
        return name;
    }

    /**
     * Returns the unique prefix character used to identify this multibase in
     * encoded strings.
     *
     * @return the multibase prefix character
     */
    public char prefix() {
        return prefix;
    }

    /**
     * Returns the length of the base alphabet (e.g., 32, 58, 64).
     *
     * @return the length of the encoding alphabet
     */
    public int length() {
        return length;
    }

    /**
     * Checks whether the given string is encoded with this multibase.
     *
     * @param encoded the string to test
     * @return {@code true} if the string is encoded using this multibase,
     *         {@code false} otherwise
     */
    public boolean isEncoded(final String encoded) {
        return encoded != null
                && encoded.trim().length() > 0
                && prefix == encoded.charAt(0);
    }

    /**
     * Decodes the given multibase-encoded string into a byte array.
     *
     * @param encoded the multibase-encoded string
     * @return the decoded byte array
     * @throws IllegalArgumentException if the input is {@code null}, empty, or has
     *                                  an incorrect prefix
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
     * Encodes the given byte array into a multibase-encoded string.
     *
     * @param data the byte array to encode
     * @return the encoded string, including the base prefix
     * @throws IllegalArgumentException if the input is {@code null} or empty
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

    /**
     * Returns a string representation of this {@code Multibase}.
     *
     * @return a string containing the prefix and base length
     */
    @Override
    public String toString() {
        return "Multibase [prefix=" + prefix + ", length=" + length + "]";
    }

    /**
     * Returns a list of all provided {@code Multibase} instances.
     *
     * @return an array of supported multibase instances
     */
    public static Multibase[] provided() {
        return ALL;
    }
}
