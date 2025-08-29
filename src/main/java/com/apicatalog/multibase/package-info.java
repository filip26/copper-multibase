/**
 * Support for <a href="https://multiformats.io/multibase/">multibase</a>.
 *
 * <p>
 * Multibase is a <em>self-describing format</em> for encoding binary data as
 * text. A multibase-encoded string is prefixed with a single character that
 * uniquely identifies the base encoding (e.g., {@code 'z'} for Base58-BTC,
 * {@code 'm'} for Base64). This allows encoded data to carry along the
 * information needed for correct decoding.
 * </p>
 *
 * <p>
 * This package provides:
 * </p>
 * <ul>
 * <li>{@link com.apicatalog.multibase.Multibase} – definition of an encoding
 * format including its name, prefix, alphabet length, and encode/decode
 * functions.</li>
 * <li>{@link com.apicatalog.multibase.MultibaseDecoder} – utility for resolving
 * a registered encoding from its prefix or name and decoding multibase-encoded
 * strings.</li>
 * </ul>
 *
 * <p>
 * A set of built-in multibase encodings is available via
 * {@link com.apicatalog.multibase.Multibase#provided()}.
 * </p>
 */
package com.apicatalog.multibase;
