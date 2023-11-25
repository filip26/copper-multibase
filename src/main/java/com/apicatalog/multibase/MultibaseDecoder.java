package com.apicatalog.multibase;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MultibaseDecoder {

    protected final Map<Character, Multibase> bases;

    protected MultibaseDecoder(Map<Character, Multibase> bases) {
        this.bases = bases;
    }

    /**
     * A new instance initialized with all supported bases.
     * 
     * @return a new instance
     */
    public static MultibaseDecoder getInstance() {
        return getInstance(Multibase.BASE_58_BTC);
    }

    /**
     * A new instance initialized with the given bases.
     * 
     * @param bases to initialize the decoder
     * @return a new instance
     */
    public static MultibaseDecoder getInstance(Multibase... bases) {
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
            throw new IllegalArgumentException("The encoded value be non empty string.");
        }

        return Optional.ofNullable(bases.get(encoded.charAt(0)));
    }

    public byte[] decode(String encoded) {
        return getBase(encoded)
                .map(base -> base.decode(encoded))
                .orElseThrow(() -> new IllegalArgumentException("Unsupported multibase encoding [" + encoded.charAt(0) + "]."));
    }
}
