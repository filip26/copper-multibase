package com.apicatalog.codec;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.apicatalog.codec.Base32Id;

@RunWith(JUnit4.class)
public class Base32IdTest {

    @Test
    public void testAlphabet() {
    	Assert.assertEquals("N", Base32Id.encodeLong(2));
    	Assert.assertEquals("S", Base32Id.encodeLong(22));
    	Assert.assertEquals("9", Base32Id.encodeLong(31));
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
    	Assert.assertEquals(1, Base32Id.encodeLong(31l).length());
    	Assert.assertEquals(13, Base32Id.encodeLong(Long.MAX_VALUE).length());
    }
    
    @Test
    public void testMinMax() {
    	encodeDecode(0l);
    	encodeDecode(Long.MAX_VALUE);
    }

    @Test
    public void testAutoCorrection() {
    	Assert.assertEquals(Base32Id.decodeLong("1PH0NE"), Base32Id.decodeLong("iphone"));
    }
    
    @Test
    public void testExcludedLettersAU() {
    	try {
    		Base32Id.decodeLong("AU");
    		Assert.fail();
    	} catch (IllegalArgumentException e) {
    	}
    }

    @Test
    public void testExcludedLettersA() {
    	try {
    		Base32Id.decodeLong("A");
    		Assert.fail();
    	} catch (IllegalArgumentException e) {
    	}
    }

    @Test
    public void testExcludedLettersU() {
    	try {
    		Base32Id.decodeLong("U");
    		Assert.fail();
    	} catch (IllegalArgumentException e) {
    	}
    }
    
    static void encodeDecode(Long number) {
    	
    	final String e1 = Base32Id.encodeLong(number);
    	Assert.assertNotNull(e1);
    	
    	System.out.println("encode(" + number + "l) = " + e1 +  "\t ("  + String.format("%.2f", 100*((double)e1.length() / (double)(Long.toString(number).length()))) + "%)");
    	
    	final Long d1 = Base32Id.decodeLong(e1);
    	Assert.assertNotNull(d1);
    	Assert.assertEquals(number, d1);
    }
}
