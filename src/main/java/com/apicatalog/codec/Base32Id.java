package com.apicatalog.codec;

import org.apache.commons.lang3.StringUtils;

public class Base32Id {

	Base32Id() {}
	
	static final char[] ALPHABET = new char[] {
			'Y', 'B', 'N', 'D', 'R',
			'F', 'G', '8', 'E', 'J',
			'K', 'M', 'C', 'P', 'Q', 
			'X', '0', 'T', '1', 'V', 
			'W', 'L', 'S', 'Z', '2',
			'3', '4', '5', 'H', '7', 
			'6', '9', 
	};
	
	static final int[] ALPHA_INDEX = new int[] {
			-1,  1, 12,  3,  8,		// A B C D E
			 5,  6, 28, -1,  9, 	// F G H I J
			10, 21, 11,  2, -1, 	// K L M N O
			13, 14,  4, 22, 17, 	// P Q R S T
			-1, 19, 20, 15,  0, 	// U V W X Y
			23,				 		// Z
	};	
	
	static final int[] NUM_INDEX = new int[] {
			16, 18, 24, 25, 26,		// 0 1 2 3 4
			27, 30, 29,  7, 31		// 5 6 7 8 9
	};
		
	static final int MAX_ENCODED_LONG_SIZE = 13;
	 
	public static final String encodeLong(long numericId) {

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

			remainder = (int)(result % ALPHABET.length);

			chars[MAX_ENCODED_LONG_SIZE - 1 - index] = ALPHABET[remainder];
			result /= ALPHABET.length;
			
			index += 1;
						
		} while (result > 0);
		
		return String.copyValueOf(chars, MAX_ENCODED_LONG_SIZE - index, index);
	}
	
	public static final Long decodeLong(String encoded) {

		if (StringUtils.isBlank(encoded)) {
			throw new IllegalArgumentException("Encoded number must not be a null");
		}
		if (encoded.length() > MAX_ENCODED_LONG_SIZE) {
			throw new IllegalArgumentException("Encoded number length (" + encoded.length() + ") exceeds maximal allowed length (" + MAX_ENCODED_LONG_SIZE + "), input='" + encoded + "'");
		}

		Long number = 0l;
		Long exponent = 1l;
		
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
				
				value = ALPHA_INDEX[ch - 'A'];
	
			} else if (ch >= '0' && ch <= '9') {
				
				value = NUM_INDEX[ch - '0'];
				
			}
			if (value == -1) {
				throw new IllegalArgumentException("Unsupported character '" + ch + "', index=" + encoded.length() + ", input='" + encoded + "'");
			}

			number += exponent * value;
			exponent *= ALPHABET.length;			
		}
		return number;
	}	
}
