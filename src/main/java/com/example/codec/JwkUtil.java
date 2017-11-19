package com.example.codec;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.PasswordLookup;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

public class JwkUtil {
    public static void main(String[] args) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");

// If you need a password to unlock the key store
        char[] password = "mystorepass".toCharArray();

// Load the key store from file
        keyStore.load(new FileInputStream("D:\\github\\RSA\\bin\\appweb.jks"), password);




// Extract keys and output into JWK set; the secord parameter allows lookup
// of passwords for individual private and secret keys in the store
        JWKSet jwkSet = JWKSet.load(keyStore, new PasswordLookup() {
            @Override
            public char[] lookupPassword(String name) {
                return "mykeypass".toCharArray();
            }
        });
        System.out.println(jwkSet);
    }
}
