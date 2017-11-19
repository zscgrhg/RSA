package com.example.codec;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Base64;

public class RsaCrypto {

    private final Crypto privateCrypto;
    private final Crypto publicCrypto;

    public static class PrivateCrypto implements Crypto {
        private final PrivateKey privateKey;

        public PrivateCrypto(PrivateKey privateKey) {
            this.privateKey = privateKey;
        }

        public byte[] decrypt(byte[] encrypted) throws Exception {
            Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
            cipher.init(cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(encrypted);
        }

        public byte[] encrypt(byte[] data) throws Exception {
            Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return cipher.doFinal(data);
        }
    }

    public static class PublicCrypto implements Crypto {
        private final PublicKey publicKey;

        public PublicCrypto(PublicKey publicKey) {
            this.publicKey = publicKey;
        }

        public byte[] decrypt(byte[] encrypted) throws Exception {
            Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return cipher.doFinal(encrypted);
        }

        public byte[] encrypt(byte[] data) throws Exception {
            Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        }
    }

    public RsaCrypto(String keystorePath, String keystorePass, String keyPass, String alias) throws Exception {

        KeyStore keyStore = getKeyStore(keystorePath, keystorePass);
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, keyPass.toCharArray());
        Certificate certificate = keyStore.getCertificate(alias);
        PublicKey publicKey = certificate.getPublicKey();
        this.privateCrypto = new PrivateCrypto(privateKey);
        this.publicCrypto = new PublicCrypto(publicKey);
    }

    private KeyStore getKeyStore(String keystorePath, String keystorePass) throws Exception {
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream in = new FileInputStream(keystorePath);
        ks.load(in, keystorePass.toCharArray());
        in.close();
        return ks;
    }

    public Crypto getPrivate() {
        return privateCrypto;
    }

    public Crypto getPublic() {
        return publicCrypto;
    }

    public static void main(String[] args) throws Exception {
        String keystorePath = "D:\\github\\rsaencoder\\bin\\appweb.jks";
        String keystorePass = "mystorepass";
        String keyPass = "mykeypass";
        String alias = "appweb";
        RsaCrypto rsaCrypto = new RsaCrypto(keystorePath, keystorePass, keyPass, alias);
        byte[] utf8s = rsaCrypto.getPublic().encrypt("nihao123".getBytes("utf8"));
        byte[] decrypt = rsaCrypto.getPrivate().decrypt(utf8s);
        System.out.println(new String(decrypt, "utf8"));
        String base64 = "Y9/htZ6umL9JYNrNhxEz3HS4N2pQqbShCD18WTt8wgdYVvAXgxbHs7Dd+z9j46e/gdmEA4QUHdD643WVvoKQqDmxnO2EyaVkRrdTW8zuVVtaGaIMF14cKXEK" +
                "G5S4f6npTNERRLyqE+AzzbHBYRkVM0BVy2Dek1JDDSYNKEfnbxE=";
        byte[] decrypt1 = rsaCrypto.getPrivate().decrypt(Base64.getDecoder().decode(base64));
        System.out.println(new String(decrypt1, "utf8"));
    }
}