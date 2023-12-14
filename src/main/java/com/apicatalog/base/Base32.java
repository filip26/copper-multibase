package com.apicatalog.base;

/*
 * https://datatracker.ietf.org/doc/html/rfc4648
 */
public class Base32 {

    static char[] ALPHABET = new char[] {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2',
            '3', '4', '5', '6', '7',
    };
    
    static int[] PADDING = new int[] {
        6, 4, 3, 1, 0,
    };

    public static String encode(byte[] data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (data.length == 0) {
            return "";
        }

        final StringBuilder encoded = new StringBuilder();

        int rest = -1;
        
        for (int index = 0; index < data.length; index++) {

            int shift = index % 5;

            switch (shift) {
            case 0:
                encoded.append(ALPHABET[data[index] >>> 3]);
                rest = (0x07 & data[index]) << 2; 
                break;
                
            case 1:
                encoded.append(ALPHABET[rest | (data[index] >>> 6)]);
                encoded.append(ALPHABET[(0x3f & data[index]) >>> 1]);
                rest = (0x01 & data[index]) << 4;
                break;
                
            case 2:
                encoded.append(ALPHABET[rest | (data[index] >>> 4)]);
                rest = (0x0f & data[index]) << 1;
                break;

            case 3:
                encoded.append(ALPHABET[rest | (data[index] >>> 7)]);
                encoded.append(ALPHABET[(0x7f & data[index]) >>> 2]);
                rest = (0x03 & data[index]) << 3;
                break;

            case 4:
                encoded.append(ALPHABET[rest | (data[index] >>> 5)]);
                encoded.append(ALPHABET[0x1f & data[index]]);
                rest = -1;
                break;
            }
        }

        if (rest != -1) {
            encoded.append(ALPHABET[rest]);
        }

        // pads
        for (int index = 0; index < (PADDING[(data.length - 1) % 5]); index++) {
            encoded.append('=');
        }

        return encoded.toString();
    }

    public static byte[] decode(String encoded) {
        // TODO Auto-generated method stub
        return null;
    }

}
