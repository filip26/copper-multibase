# Copper Multibase

Multibase is a **self-describing** format for representing binary data as text. Each multibase-encoded string begins with a single character that uniquely identifies the base encoding (e.g., `z` for Base58BTC, `m` for Base64).  

This design allows encoded data to carry the information needed for correct decoding, ensuring clarity, interoperability, and extensibility across systems.

[![Java 8 CI](https://github.com/filip26/copper-multibase/actions/workflows/java8-build.yml/badge.svg?branch=main)](https://github.com/filip26/copper-multibase/actions/workflows/java8-build.yml)
[![javadoc](https://javadoc.io/badge2/com.apicatalog/copper-multibase/javadoc.svg)](https://javadoc.io/doc/com.apicatalog/copper-multibase)
[![Maven Central](https://img.shields.io/maven-central/v/com.apicatalog/copper-multibase.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:com.apicatalog%20AND%20a:copper-multibase)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## ✨ Features
- `static` registry
  - no lookups when encoding
  - direct static access to an encoder
  - configurable set of encodings to support when decoding
- no 3rd party dependencies
- easily extendable

## ⏱️ Algorithms

| Name | Algorithm | $O$ |
| :--- | :--- | :--- |
| Base2  | Bitwise Mapping (1-bit) | $O(n)$ |
| Base16 `lower/upper` | Bitwise Mapping (4-bit) | $O(n)$ |
| Base32 `lower/upper, [no-]padding` | Bitwise Mapping (5-bit) | $O(n)$ |
| Base32Hex `lower/upper, [no-]padding` | Bitwise Mapping (5-bit) | $O(n)$ |
| Base58BTC | Arbitrary-precision Radix Conversion | $O(n²)$ |
| Base64 `[no-]padding` | Bitwise Mapping (6-bit) | $O(n)$ |
| Base64URL `[no-]padding` | Bitwise Mapping (6-bit) | $O(n)$ |

## 💡 Examples
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

## 📦 Installation

### Maven

To include Copper Multibase in your project, add the following dependency to your pom.xml:

```xml
<dependency>
    <groupId>com.apicatalog</groupId>
    <artifactId>copper-multibase</artifactId>
    <version>4.1.0</version>
</dependency>
```

## 🛠️ LD-CLI

[LD-CLI](https://github.com/filip26/ld-cli) is a command-line utility for
working with multiformats including multibase, multicodec, and multihash,
as well as JSON-LD and related specifications.

It provides encoding, decoding, detection, analysis, and format conversion
features, making it useful for inspecting identifiers, testing content
addressing, and integrating multiformats into development workflows.

### Example

Detect and analyze a multibase + multicodec value
```bash
> ld-cli multicodec --analyze --multibase <<< 'z6MkmM42vxfqZQsv4ehtTjFFxQ4sQKS2w6WR7emozFAn5cxu'

Multibase:  name=base58btc, prefix=z, length=58 chars
Multicodec: name=ed25519-pub, code=237, varint=[0xED,0x01], tag=Key, status=Draft
Length:     32 bytes
```

## 🤝 Contributing

Contributions are welcome! Please submit a pull request.

### Building

Fork and clone the repository, then build with Maven:

```bash
> cd copper-multibase
> mvn package
```


## 📚 Resources
- [Copper Multicodec](https://github.com/filip26/copper-multicodec)
- [W3C CCG Multibase](https://github.com/w3c-ccg/multibase)
- [Multiformats Multibase](https://github.com/multiformats/multibase)
- [java-multibase](https://github.com/multiformats/java-multibase)


## 💼 Commercial Support

Commercial support and consulting are available.  
For inquiries, please contact: filip26@gmail.com
