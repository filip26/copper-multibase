# Copper Multibase Encoder and Decoder.

A Java implementation of [Multibase](https://github.com/multiformats/multibase).

[![Java 8 CI](https://github.com/filip26/copper-multibase/actions/workflows/java8-build.yml/badge.svg)](https://github.com/filip26/copper-multibase/actions/workflows/java8-build.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.apicatalog/copper-multibase.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:com.apicatalog%20AND%20a:copper-multibase)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

### Features
- `static` registry
  - no lookups when encoding
  - direct static access to an encoder
  - confirugable set of encodings to support when decoding
- Base16 `(lower|upper)`
- Base32 `(lower|upper, [no-]padding)`
- Base32Hex `(lower|upper, [no-]padding)`
- Base64 `([no-]padding)`
- Base64URL `([no-]padding)`
- Base58BTC

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
- [Multibase](https://github.com/multiformats/multibase)
- [java-multibase](https://github.com/multiformats/java-multibase)


