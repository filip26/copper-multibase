/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.apicatalog.base;

import java.util.stream.IntStream;

/**
 * Numeric id encoder/decoder utilizing Base32 algorithm.
 */
public final class Id32 {

	private Id32() {}

	public static final int ALPHABET_SIZE = 32;

	public static final int MAX_ENCODED_LONG_SIZE = 13;

	/**
	 * Encodes {@code long} into {@code String} with the default alphabet.
	 * 
	 * @param number to encode
	 * @return the encoded number 
	 */
	public static final String encodeLong(final long number) {
	    return encodeLong(number, Alphabet.DEFAULT);
	}

	/**
     * Encodes {@code long} into {@code String} with the given alphabet.
     * 
     * @param number to encode
     * @param alphabet to use
     * @return the encoded number 
     */
	public static final String encodeLong(final long number, final Alphabet alphabet) {

		if (number < 0) {
			throw new IllegalArgumentException("An identifier [" + number + "] must be equal or greater than zero.");
		}
		
		long result = number;
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

    /**
     * Decodes {@code long} from {@code String} with the default alphabet.
     * 
     * @param encoded number to decode
     * @return the decoded number
     */
	public static final long decodeLong(final String encoded) {
	    return decodeLong(encoded, Alphabet.DEFAULT);
	}
	
	/**
     * Decodes {@code long} from {@code String} with the given alphabet.
     * 
     * @param encoded number to decode
     * @param alphabet to use
     * @return the decoded number
     */
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
           
           final char ch = encoded.charAt(index);
                      
           final int value = alphabet.indexOf(ch);

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
       
       public int indexOf(final char ch) {

           if (ch == 'I' || ch == 'i') { // I,i -> 1
               return numbers[1];
           } 
           
           if (ch == 'O' || ch == 'o') { // O,o -> 0
               return numbers[0];
           } 
           
           if (ch >= 'A' && ch <= 'Z') {
               return alphas[ch - 'A'];
           }

           if (ch >= 'a' && ch <= 'z') {
               return alphas[ch - 'a'];
           }

           if (ch >= '0' && ch <= '9') {    
               return numbers[ch - '0'];
           }
           
           return -1;
       }
   }
}
