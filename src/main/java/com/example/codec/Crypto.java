package com.example.codec;

public interface Crypto {
    byte[] decrypt(byte[] encrypted)throws Exception;
    byte[] encrypt(byte[] data) throws Exception;
}
