package com.apicatalog.codec;

import org.apache.commons.lang3.StringUtils;

public class Base32Id {

	final static char[] ALPHABET = new char[] {
			'B', 'C', 'D', 'E', 
			'F', 'G', 'H', 'J', 'K',
			'L',
			'M', 'N', 'P', 'Q', 'R', 
			'S', 'T', 'V', 'W', 'X', 
			'Y', 'Z',
			'0', '1', '2', '3', '4', 
			'5', '6', '7', '8', '9'
	};
		
	final static int MAX_ENCODED_LONG_SIZE = 13;
	 
	public final static String encode(long numericId) {

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
	
	public static final Long decode(String encoded) {

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
			
			if (ch >= 'b' && ch <= 'z') {
				ch = Character.toUpperCase(ch);
			}
			
			if (ch == 'I') {
				ch = '1';
				
			} else if (ch == 'O') {
				ch = '0';
			} 
			
			int value = -1;
			
			if (ch > 'A' && ch <= 'Z' && ch != 'U') {
				
				value = ch  
						- (ch > 'I' ? 1 : 0)
						- (ch > 'O' ? 1 : 0)
						- (ch > 'U' ? 1 : 0)
						- 'B'
						;
	
			} else if (ch >= '0' && ch <= '9') {
				
				value = ch - '0' + 22;
				
			} else {
				throw new IllegalArgumentException("Unsupported character '" + ch + "', index=" + encoded.length() + ", input='" + encoded + "'");
			}

			number += exponent * value;
			exponent *= ALPHABET.length;
			
		}
		return number;
	}	
}
