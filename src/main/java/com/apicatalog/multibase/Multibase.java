package com.apicatalog.multibase;

import java.util.Objects;
import java.util.function.Function;

import com.apicatalog.base.Base58;

/**
 * Represents multibase encoding.
 */
public class Multibase {

    public static final Multibase BASE_58_BTC = new Multibase('z', 58, Base58::decode, Base58::encode);

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
     * An encoding length. e.g. 32, 58, 64.
     * 
     * @return the encoding length
     */
    public int length() {
        return length;
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
