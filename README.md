# Copper Multibase Encoder and Decoder.

Multibase is a **self-describing format** for encoding binary data as text. A multibase-encoded string is prefixed with a single character that uniquely identifies the base encoding (e.g., `z` for Base58BTC, `m` for Base64). This allows encoded data to carry along the information needed for correct decoding.

[![Java 8 CI](https://github.com/filip26/copper-multibase/actions/workflows/java8-build.yml/badge.svg)](https://github.com/filip26/copper-multibase/actions/workflows/java8-build.yml)
[![javadoc](https://javadoc.io/badge2/com.apicatalog/copper-multibase/javadoc.svg)](https://javadoc.io/doc/com.apicatalog/copper-multibase)
[![Maven Central](https://img.shields.io/maven-central/v/com.apicatalog/copper-multibase.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:com.apicatalog%20AND%20a:copper-multibase)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Features
- `static` registry
  - no lookups when encoding
  - direct static access to an encoder
  - configurable set of encodings to support when decoding
- built-in bases
  - Base2
  - Base16 `(lower|upper)`
  - Base32 `(lower|upper, [no-]padding)`
  - Base32Hex `(lower|upper, [no-]padding)`
  - Base58BTC
  - Base64 `([no-]padding)`
  - Base64URL `([no-]padding)`
- no 3rd party dependencies
- easily extendable

## Examples
```javascript
/* encode an input with Base58BTC */
String encoded = Multibase.BASE_58_BTC.encode(byte[]);

/* get decoder instance initialized with all supported bases */
var decoder = MultibaseDecoder.getInstance();

/* get decoder initialized with custom base(s) */
var decoder = MultibaseDecoder.getInstance(mybase, ...);

/* decode */
byte[] decoded = decoder.decode(encoded);

/* or check if base is supported  */
byte[] decoded = decoder.getBase(encoded)
                        .map(base -> base.decode(encoded))
                        .orElseThrow(() -> new IllegalArgumentException("Unsupported base."));

/* or directy when only one base is supported */
byte[] decoded = Multibase.BASE_58_BTC.decode(encoded);

/* check if encoded with a base */
if (Multibase.BASE_58_BTC.isEncoded(encoded)) {
  ...
}

/* a cutom base implementation */
var mybase = new Multibase(
                     name,     // the unique base name (e.g., "base64urlpad")
                     prefix,   // the unique prefix character indicating the base
                     length,   // the base alphabet length
                     string -> byte[], // the decoding function
                     byte[] -> string  // the encoding function
                     );

/* encode with a custom base */
String encoded = mybase.encode(byte[]);

/* directly decode with a custom base */
byte[] decoded = mybase.decode(encoded);

```

## Installation

### Maven

```xml
<dependency>
    <groupId>com.apicatalog</groupId>
    <artifactId>copper-multibase</artifactId>
    <version>4.1.0</version>
</dependency>
```

## Contributing

All PR's welcome!


### Building

Fork and clone the project repository.

```bash
> cd copper-multibase
> mvn clean package
```


## Resources
- [Copper Multicodec](https://github.com/filip26/copper-multicodec)
- [W3C CCG Multibase](https://github.com/w3c-ccg/multibase)
- [Multiformats Multibase](https://github.com/multiformats/multibase)
- [java-multibase](https://github.com/multiformats/java-multibase)


