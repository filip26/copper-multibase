package com.apicatalog.multibase;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Decodes multibase-encoded strings using the {@link Multibase} encodings
 * explicitly registered via prefix characters.
 * <p>
 * The decoder maps prefix characters (e.g., {@code 'z'}, {@code 'm'},
 * {@code 'f'}) to {@link Multibase} instances. When decoding, it uses the first
 * character of the input string to select the matching encoding and decode the
 * remaining data.
 * </p>
 */
public class MultibaseDecoder {

    protected final Map<Character, Multibase> bases;

    /**
     * Constructs a {@code MultibaseDecoder} with the provided base prefix mappings.
     *
     * @param bases a map of prefix characters to {@link Multibase} instances
     */
    protected MultibaseDecoder(final Map<Character, Multibase> bases) {
        this.bases = bases;
    }

    /**
     * Creates a {@code MultibaseDecoder} instance configured with the encodings
     * returned by {@link Multibase#provided()}.
     *
     * @return a new decoder instance
     */
    public static MultibaseDecoder getInstance() {
        return getInstance(Multibase.provided());
    }

    /**
     * Creates a {@code MultibaseDecoder} using only the provided encodings.
     *
     * @param bases the encodings to register for decoding
     * @return a new decoder instance
     */
    public static MultibaseDecoder getInstance(final Multibase... bases) {
        return new MultibaseDecoder(
                Arrays.stream(bases)
                        .collect(Collectors.toMap(Multibase::prefix, Function.identity())));
    }

    /**
     * Returns the {@link Multibase} encoding associated with the given prefix
     * character, if it was registered when this decoder was created.
     *
     * @param prefix the multibase prefix character
     * @return an {@code Optional} containing the corresponding {@link Multibase},
     *         or empty if not registered
     */
    public Optional<Multibase> getBase(final char prefix) {
        return Optional.ofNullable(bases.get(prefix));
    }

    /**
     * Detects the encoding used in the given string based on the prefix character,
     * and returns the corresponding {@link Multibase} if it was registered.
     *
     * @param encoded a multibase-encoded string
     * @return an {@code Optional} containing the corresponding {@link Multibase},
     *         or empty if not registered
     * @throws IllegalArgumentException if {@code encoded} is null or empty
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
     * Decodes a multibase-encoded string into a byte array.
     *
     * @param encoded the encoded string to decode
     * @return the decoded byte array
     * @throws IllegalArgumentException if the encoding is not registered or input
     *                                  is invalid
     */
    public byte[] decode(final String encoded) {
        return getBase(encoded)
                .map(base -> base.decode(encoded))
                .orElseThrow(() -> new IllegalArgumentException("Unsupported multibase encoding [" + encoded.charAt(0) + "]."));
    }
}
