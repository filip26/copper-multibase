package com.apicatalog.multibase;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MultibaseDecoder {

    protected final Map<Character, Multibase> bases;

    protected MultibaseDecoder(final Map<Character, Multibase> bases) {
        this.bases = bases;
    }

    /**
     * A new instance initialized with all supported bases.
     * 
     * @return a new instance
     */
    public static MultibaseDecoder getInstance() {
        return getInstance(
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
                Multibase.BASE_2);
    }

    /**
     * A new instance initialized with the given bases.
     * 
     * @param bases to initialize the decoder
     * @return a new instance
     */
    public static MultibaseDecoder getInstance(final Multibase... bases) {
        return new MultibaseDecoder(
                Arrays.stream(bases)
                        .collect(Collectors.toMap(Multibase::prefix, Function.identity())));
    }

    /**
     * Tries to detect the base used for encoding.
     * 
     * @param encoded an encoded value
     * @return detected base encoding or {@link Optional#empty()}
     */
    public Optional<Multibase> getBase(final String encoded) {

        if (encoded == null) {
            throw new IllegalArgumentException("The encoded value must not be null.");
        }

        if (encoded.trim().length() == 0) {
            throw new IllegalArgumentException("The encoded value must be non empty string.");
        }

        return getBase(encoded.charAt(0));
    }
    
    /**
     * Returns a base associated with the given prefix.
     * 
     * @param prefix associated with the base encoding
     * @return base encoding or {@link Optional#empty()}
     */
    public Optional<Multibase> getBase(final char prefix) {
        return Optional.ofNullable(bases.get(prefix));        
    }

    public byte[] decode(final String encoded) {
        return getBase(encoded)
                .map(base -> base.decode(encoded))
                .orElseThrow(() -> new IllegalArgumentException("Unsupported multibase encoding [" + encoded.charAt(0) + "]."));
    }
}
