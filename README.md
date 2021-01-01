## Numeric Id Encoder/Decoder.

Just another variation on Base32 encoding.

![Java CI with Maven](https://github.com/filip26/id32/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=filip26_id32&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=filip26_id32)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=filip26_id32&metric=coverage)](https://sonarcloud.io/dashboard?id=filip26_id32)
[![Maven Central](https://img.shields.io/maven-central/v/com.apicatalog/id32.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.apicatalog%22%20AND%20a:%22id32)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

### Goals
- ``URL`` / ``URI`` / ``IRI`` safe
- one case letters and digits only
- low transcription error rate
- low risk of accidental profanity

### Examples
```java
// default alphabet
Id32.encodeLong(823543l) : "3R8Z"
Id32.encodeLong(8922003266371364727l) : "8ZWK1SC2XBJ5Z"
                
Id32.decodeLong("JKYZ3YY") : 10000000000l
Id32.decodeLong("BYYYYY")  : 33554432l

Id32.decodeLong("iphone") == Id32.decodeLong("1PH0NE") : 618545224

// custom alphabet
Id32.encodeLong(123456l, Alphabet.of('X', 'Z', ...));
```

### Default Alphabet
```
	'Y', 'B', 'N', 'D', 'R',
	'F', 'G', '8', 'E', 'J',
	'K', 'M', 'C', 'P', 'Q', 
	'X', '0', 'T', '1', 'V', 
	'W', 'L', 'S', 'Z', '2',
	'3', '4', '5', 'H', '7', 
	'6', '9' 
```
Letters A,I,O and U are excluded.
