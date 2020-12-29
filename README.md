![Java CI with Maven](https://github.com/filip26/id32/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Id32 encoder/decoder.

Just another variation on Base32 encoding. Numeric id shortener.

### Goals
- ``URL`` / ``URI`` / ``IRI`` safe
- one case letters and digits only
- low transcription error rate
- low risk of accidental profanity

### Examples
```java
// default alphabet
Id32.encodeLong(14348907l)     	: "PLHVM"
Id32.encodeLong(205891132094649l) 	: "F5E8BHW6F3"

Id32.decodeLong("JKYZ3YY") : 10000000000l
Id32.decodeLong("BYYYYY")  : 33554432l

Id32.decodeLong("iphone") == Id32.decodeLong("1PH0NE") : 618545224
Id32.decodeLong("AU") throws IllegalArgumentException

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
