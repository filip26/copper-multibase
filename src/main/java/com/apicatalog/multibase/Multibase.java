package com.apicatalog.multibase;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.apicatalog.base.Base58;

public class Multibase {

    public static final Base BASE_58_BTC = new Base('z', 58, Base58::decode, Base58::encode);

    protected final Map<Character, Base> bases;

    protected Multibase(Map<Character, Base> bases) {
        this.bases = bases;
    }

    public static Multibase getInstance(Base... bases) {
        return new Multibase(
                Arrays.stream(bases)
                        .collect(Collectors.toMap(Base::prefix, Function.identity())));
    }

    public Optional<Base> getBase(final String encoded) {

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
