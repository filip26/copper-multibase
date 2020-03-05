## Base32Id encoder/decoder.

Just another variation on Base32 encoding.

### Goals
- ``URL`` / ``URI`` / ``IRI`` safe
- one case letters and digits only
- low transcription error rate
- low risk of accidental profanity

### Examples
```java
Base32Id.encodeLong(117649l)     : "EW6V"
Base32Id.encodeLong(1977326743l) : "C471JF1"

Base32Id.decodeLong("73ZWTB")    : 1000000000
Base32Id.decodeLong("CBBBBBBB")  : 34359738368l


Base32Id.decodeLong("iphone") == Base32Id.decodeLong("1PH0NE") : 784554339
```

### Alphabet
```
	'B', 'C', 'D', 'E', 'F', 
	'G', 'H', 'J', 'K', 'L',
	'M', 'N', 'P', 'Q', 'R', 
	'S', 'T', 'V', 'W', 'X', 
	'Y', 'Z',
	'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
```
Letters A,I,O and U are excluded.
