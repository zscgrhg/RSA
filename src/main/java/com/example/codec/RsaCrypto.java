package com.example.codec;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.spec.MGF1ParameterSpec;
import java.util.Base64;

public class RsaCrypto {

    public static final String OAEP_ALG="RSA/ECB/OAEPWithSHA-1AndMGF1Padding";
    private final Crypto privateCrypto;
    private final Crypto publicCrypto;

    public static class PrivateCrypto implements Crypto {
        private final PrivateKey privateKey;

        public PrivateCrypto(PrivateKey privateKey) {
            this.privateKey = privateKey;
        }

        public byte[] decrypt(byte[] encrypted) throws Exception {
            Cipher cipher = Cipher.getInstance(OAEP_ALG);
            cipher.init(cipher.DECRYPT_MODE, privateKey, new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256,
                    PSource.PSpecified.DEFAULT));
            return cipher.doFinal(encrypted);
        }

        public byte[] encrypt(byte[] data) throws Exception {
            Cipher cipher = Cipher.getInstance(OAEP_ALG);
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
            Cipher cipher = Cipher.getInstance(OAEP_ALG);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return cipher.doFinal(encrypted);
        }

        public byte[] encrypt(byte[] data) throws Exception {
            Cipher cipher = Cipher.getInstance(OAEP_ALG);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        }
    }

    public RsaCrypto(String keystorePath, String keystorePass, String keyPass, String alias) throws Exception {
        KeyStore keyStore = getKeyStore(keystorePath, keystorePass);
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, keyPass.toCharArray());
        Certificate certificate = keyStore.getCertificate(alias);
        PublicKey publicKey = certificate.getPublicKey();
        System.out.println(privateKey.getAlgorithm());
        System.out.println(publicKey.getAlgorithm());
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
        String data="kkHhA7yrHC2yh5fL25q8Q+3UdWtb+oH8Rrrg8yQ3A45D7Ra58ITrza2YRC3+rMIZj4zyv1vBGtKF/3cERGcYevYQNYvhINTYuYjmoyBoxd97v8Y3e+IIm0pSTNL5BYgYCmUFJWJgOQUUJmDY9JbGsZqvISuL+SehCJL5Qkm8nGo=";
        rsaCrypto.getPrivate().decrypt(Base64.getDecoder().decode(data));
        /*for (int i = 0; i < 10; i++) {
            byte[] utf8s = rsaCrypto.getPublic().encrypt("nihao123".getBytes("utf8"));
            System.out.println(Base64.getEncoder().encodeToString(utf8s));
            byte[] decrypt = rsaCrypto.getPrivate().decrypt(utf8s);
            System.out.println(new String(decrypt, "utf8"));
        }*/
    }
}
