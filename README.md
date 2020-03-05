## Base32Id encoder/decoder.

Just another variation on Base32 encoding.

### Goals
- ``URL`` / ``URI`` / ``IRI`` safe
- low transcription error rate (I and O letters)  
- low risk of accidental profanity (A and U letters)
- all one case


### Examples
```java
Base32Id.encode(117649l)     : "EW6V"
Base32Id.encode(1977326743l) : "C471JF1"

Base32Id.decode("73ZWTB")    : 1000000000
Base32Id.decode("CBBBBBBB")  : 34359738368l


Base32Id.decode("iphone") = Base32Id.decode("1PH0NE") : 784554339
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
