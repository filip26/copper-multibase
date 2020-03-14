## Base32Id encoder/decoder.

Just another variation on Base32 encoding.

### Goals
- ``URL`` / ``URI`` / ``IRI`` safe
- one case letters and digits only
- low transcription error rate
- low risk of accidental profanity

### Examples
```java
Base32Id.encodeLong(14348907l)     	: "PLHVM"
Base32Id.encodeLong(205891132094649l) 	: "F5E8BHW6F3"

Base32Id.decodeLong("JKYZ3YY") : 10000000000l
Base32Id.decodeLong("BYYYYY")  : 33554432l

Base32Id.decodeLong("iphone") == Base32Id.decodeLong("1PH0NE") : 618545224
Base32Id.decodeLong("AU") throws IllegalArgumentException
```

### Alphabet
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
