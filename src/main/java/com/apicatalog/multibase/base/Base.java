package com.apicatalog.multibase.base;

/**
 * Represents multibase encoding.
 */
public interface Base {

    /**
     * Base encoding name. Should be unique. e.g. Base58BTC.
     * 
     * @return the base encoding name
     */
    String name();
    
    /**
     * A unique prefix identifying base encoding in encoded value.
     * 
     * @return the base encoding unique prefix
     */
    char prefix();
    
    /**
     * An encoding length. e.g. 32, 58, 64.
     * 
     * @return the encoding length
     */
    int length();
    
    /**
     * Decodes the given data into byte array.
     * 
     * @param data to decodes
     * @return encoded data as byte array
     */
    byte[] decode(String data);
    
    /**
     * Encodes the given data into base encoded string.
     * 
     * @param bada to encode
     * @return a string representing the encoded data
     */
    String encode(byte[] bada);
}
