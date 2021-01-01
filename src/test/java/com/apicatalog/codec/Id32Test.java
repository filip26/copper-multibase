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
package com.apicatalog.codec;

import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.apicatalog.codec.Id32.Alphabet;

@RunWith(JUnit4.class)
public class Id32Test {

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeLong() {
        Id32.encodeLong(-1l);
    }

    @Test
    public void testOneLetter() {
    	Assert.assertEquals("N", Id32.encodeLong(2));
    	Assert.assertEquals("S", Id32.encodeLong(22));
    	Assert.assertEquals("9", Id32.encodeLong(31));
    }

    @Test
    public void testPrime3() {
    	for (Long value = 3l; value > 0 && value <= Long.MAX_VALUE; value *= 3) {
    		encodeDecode(value);
    	}
    }

    @Test
    public void testPrime7() {
    	for (Long value = 7l; value > 0 && value <= Long.MAX_VALUE; value *= 7) {
    		encodeDecode(value);
    	}
    }

    @Test
    public void testExp10() {
    	for (Long value = 10l; value > 0 && value <= Long.MAX_VALUE; value *= 10) {
    		encodeDecode(value);
    	}
    }

    @Test
    public void testExp2() {
    	for (Long value = 2l; value > 0 && value <= Long.MAX_VALUE; value *= 2) {
    		encodeDecode(value);
    	}
    }
    
    @Test
    public void testLength()  {
    	Assert.assertEquals(1, Id32.encodeLong(31l).length());
    	Assert.assertEquals(13, Id32.encodeLong(Long.MAX_VALUE).length());
    }
    
    @Test
    public void testMinMax() {
    	encodeDecode(0l);
    	encodeDecode(Long.MAX_VALUE);
    }

    @Test
    public void testAutoCorrection() {
    	Assert.assertEquals(Id32.decodeLong("1PH0NE"), Id32.decodeLong("iphone"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testExcludedLettersAU() {
		Id32.decodeLong("AU");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExcludedLettersA() {
		Id32.decodeLong("A");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExcludedLettersU() {
		Id32.decodeLong("U");
    }
    
    @Test
    public void testDecodeLowerLetters() {
        Assert.assertEquals(10000000000l, Id32.decodeLong("jkyz3yy"));
    }

    @Test
    public void testDefaultAlphabetOf() {
        
        Alphabet alphabet = Alphabet.of(Alphabet.DEFAULT.characters);
        
        Assert.assertNotNull(alphabet);
        Assert.assertArrayEquals(Alphabet.DEFAULT.characters, alphabet.characters());
        Assert.assertArrayEquals(Alphabet.DEFAULT.characters, alphabet.characters);
        Assert.assertEquals(Alphabet.DEFAULT.characters.length, alphabet.length());
        Assert.assertArrayEquals(Alphabet.DEFAULT.alphas, alphabet.alphas);
        Assert.assertArrayEquals(Alphabet.DEFAULT.numbers, alphabet.numbers);
    }

    @Test
    public void testCustomAlphabetOf() {
        
        char[] characters = new char[]{
                'A', 'b', 'C', 'D', 'E', 
                'F', 'g', 'H', 'I', 'J', 
                'K', 'l', 'M', 'N', 'O',
                'P', 'q', 'R', 'S', 'T',
                'U', 'v', 'W', 'X', 'Y',
                'Z', '1', '2', '3', '4',
                '5', '6'
                };

        Alphabet alphabet = Alphabet.of(characters);
        
        Assert.assertNotNull(alphabet);
        Assert.assertArrayEquals(characters, alphabet.characters());
        Assert.assertEquals(characters.length, alphabet.length());
        Assert.assertArrayEquals(characters, alphabet.characters);
        Assert.assertArrayEquals(IntStream.range(0, 26).toArray(), alphabet.alphas);
        Assert.assertArrayEquals(new int[] {-1, 26, 27, 28, 29, 30, 31, -1, -1, -1}, alphabet.numbers);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAlphabetOf1() {
        
        Alphabet.of(new char[]{
                'A', 'b', 'C', 'D', 'E', 
                'F', 'g', 'H', 'I', 'J', 
                'K', 'l', 'M', 'N', 'O',
                'P', 'q', 'R', '#', 'T',
                'U', 'v', 'W', 'X', 'Y',
                'Z', '1', '2', '3', '4',
                '5', '6'
                });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAlphabetOf2() {
        Alphabet.of(new char[]{
                'A', 'b', 'C', 'D', 'E', 
                'Z', '1', '2', '3', '4',
                '5', '6'
                });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAlphabetOf3() {
        Alphabet.of(null);
    }

    static void encodeDecode(Long number) {
    	
    	final String e1 = Id32.encodeLong(number);
    	Assert.assertNotNull(e1);
    	
    	System.out.println("encode(" + number + "l) = " + e1 +  "\t ("  + String.format("%.2f", 100*((double)e1.length() / (double)(Long.toString(number).length()))) + "%)");
    	
    	final Long d1 = Id32.decodeLong(e1);
    	Assert.assertNotNull(d1);
    	Assert.assertEquals(number, d1);
    }
}
