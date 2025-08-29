# Copper Multibase Encoder and Decoder.

A Java implementation of [Multibase](https://w3c-ccg.github.io/multibase/).

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
var decoder = MultibaseDecoder.getInstance(cbases...);

/* decode */
byte[] decoded = decoder.decode(encoded);

/* or check if base is supported  */
Multibase base = decoder.getBase(encoded).orElseThrow(() -> new IllegalArgumentException("Unsupported base."));
byte[] decoded = base.decode(encoded);

/* or directy when only one base is supported */
byte[] decoded = Multibase.BASE_58_BTC.decode(encoded);

/* check if encoded with a base */
if (Multibase.BASE_58_BTC.isEncoded(encoded)) {
  ...
}

/* a cutom base implementation */
var mybase = new Multibase(
                     prefix,   // multibase prefix letter
                     length,   // alphabet length
                     string -> byte[], // decode fnc.
                     byte[] -> string // encode fnc.
                     );

/* encode with a custom base */
String encoded = mybase.encode(byte[]);

/* directly decode with a custom base */
byte[] decoded = mybase.decode(encoded);

/* get decoder initialized with a custom base */
var decoder = MultibaseDecorer.getInstance(mybase, ...);
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


