package com.apicatalog.multibase.base;

public class Base58BTC implements Base {
    
    protected static Base58BTC INSTANCE = new Base58BTC();
    
    protected Base58BTC() {
    }
    
    public static Base58BTC getInstance() {
        return INSTANCE;
    }
    
    @Override
    public String name() {
        return "Base58BTC";
    }

    @Override
    public char prefix() {
        return 'z';
    }

    @Override
    public int length() {
        return 58;
    }

    @Override
    public byte[] decode(String data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String encode(byte[] bada) {
        // TODO Auto-generated method stub
        return null;
    }

}
