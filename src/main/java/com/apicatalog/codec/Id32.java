package com.apicatalog.codec;

import java.util.stream.IntStream;

public final class Id32 {

	private Id32() {}

	public static final int ALPHABET_SIZE = 32;
	
	public static final int MAX_ENCODED_LONG_SIZE = 13;
	
	public static final String encodeLong(final long numericId) {
	    return encodeLong(numericId, Alphabet.DEFAULT);
	}

	public static final String encodeLong(final long numericId, final Alphabet alphabet) {

		if (numericId < 0) {
			throw new IllegalArgumentException("An identifier [" + numericId + "] must be equal or greater than zero.");
		}
		
		long result = numericId;
		int remainder = 0;
		
		char[] chars = new char[MAX_ENCODED_LONG_SIZE];
		int index = 0; 
		
		do {
			
			if (index >= MAX_ENCODED_LONG_SIZE) {
				throw new IllegalStateException();
			}

			remainder = (int)(result % alphabet.length());

			chars[MAX_ENCODED_LONG_SIZE - 1 - index] = alphabet.characters[remainder];
			result /= alphabet.length();
			
			index += 1;
						
		} while (result > 0);
		
		return String.copyValueOf(chars, MAX_ENCODED_LONG_SIZE - index, index);
	}
	
	public static final long decodeLong(final String encoded) {
	    return decodeLong(encoded, Alphabet.DEFAULT);
	}
	
   public static final long decodeLong(final String encoded, final Alphabet alphabet) {
       if (encoded == null || encoded.length() == 0) {
           throw new IllegalArgumentException("Encoded number must not be a null nor an empty string");
       }
       if (encoded.length() > MAX_ENCODED_LONG_SIZE) {
           throw new IllegalArgumentException("Encoded number length (" + encoded.length() + ") exceeds maximal allowed length (" + MAX_ENCODED_LONG_SIZE + "), input='" + encoded + "'");
       }

       long number = 0l;
       long exponent = 1l;
       
       for (int index=encoded.length() - 1; index >= 0; index--) {
           
           char ch = encoded.charAt(index);
           
           if (ch >= 'a' && ch <= 'z') {
               ch = Character.toUpperCase(ch);
           }
           
           if (ch == 'I') {
               ch = '1';
               
           } else if (ch == 'O') {
               ch = '0';
           } 
           
           int value = -1;

           if (ch >= 'A' && ch <= 'Z') {
               
               value = alphabet.alphas[ch - 'A'];
   
           } else if (ch >= '0' && ch <= '9') {
               
               value = alphabet.numbers[ch - '0'];
               
           }
           if (value == -1) {
               throw new IllegalArgumentException("Unsupported character '" + ch + "', index=" + encoded.length() + ", input='" + encoded + "'");
           }

           number += exponent * value;
           exponent *= alphabet.length();            
       }
       return number;
   }
   
   public static class Alphabet {

       public static final Alphabet DEFAULT = new Alphabet(
               new char[] {
                       'Y', 'B', 'N', 'D', 'R',
                       'F', 'G', '8', 'E', 'J',
                       'K', 'M', 'C', 'P', 'Q', 
                       'X', '0', 'T', '1', 'V', 
                       'W', 'L', 'S', 'Z', '2',
                       '3', '4', '5', 'H', '7', 
                       '6', '9', 
               },
               new int[] {
                       -1,  1, 12,  3,  8,     // A B C D E
                        5,  6, 28, -1,  9,     // F G H I J
                       10, 21, 11,  2, -1,     // K L M N O
                       13, 14,  4, 22, 17,     // P Q R S T
                       -1, 19, 20, 15,  0,     // U V W X Y
                       23,                     // Z
               },
               new int[] {
                       16, 18, 24, 25, 26,     // 0 1 2 3 4
                       27, 30, 29,  7, 31      // 5 6 7 8 9
               });

       final char[] characters;
       final int[] alphas;
       final int[] numbers;

       public Alphabet(char[] characters, int[] alpha, int[] number) {
           this.characters = characters;
           this.alphas = alpha;
           this.numbers = number;
       }
          
       public static final Alphabet of(final char...characters) {
              
           if (characters == null) {
               throw new IllegalArgumentException();
           }
           
           if (characters.length != ALPHABET_SIZE) {
               throw new IllegalArgumentException();
           }
           
           final int[] alphas = IntStream.generate(() -> -1).limit(26).toArray();
           final int[] numbers = IntStream.generate(() -> -1).limit(10).toArray();
           
           for (int i=0; i < ALPHABET_SIZE; i++) {
               if (characters[i] >= 'a' && characters[i] <= 'z') {
                   alphas[characters[i] - 'a'] = i;
                   
               } else if (characters[i] >= 'A' && characters[i] <= 'Z') {
                   alphas[characters[i] - 'A'] = i;
                   
               } else if (characters[i] >= '0' && characters[i] <= '9') {
                   numbers[characters[i] - '0'] = i;
                   
               } else {
                   throw new IllegalArgumentException();
               }
           }

           return new Alphabet(characters, alphas, numbers);
       }
       
       public long length() {
           return characters.length;
       }

       public char[] characters() {
           return characters;
       }
   }
}
