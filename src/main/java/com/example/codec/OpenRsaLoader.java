package com.example.codec;

import sun.misc.BASE64Decoder;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class OpenRsaLoader {
    public static void main(String[] args) throws Exception {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        String keyinfo = "MIIEowIBAAKCAQEAuTX5ddvxajZ/0QGGNT8sgy25GIlKmQnvc00PNbmiH6qQTJxN\n" +
                "u7nMUdL5kG31NmtyYlhPAv4wFaYJ9MKFTSWooGgA5XR0BCGbeU2tFXG3o4BxTHj4\n" +
                "YaplACPah6iTQlK/rjjhj+eW13Ri0IE6YlvrQYUkgQSt0+SdyRQH7s1I6a8nekKe\n" +
                "3gSMcpR5BKiKljUizJoOlYrx09jKkkxZ+7AIpdn6Qn4e2j3KKjcziVSGo3HxKYSm\n" +
                "yo3QP3w144kAWi7H9iWPrqj6dvNiiEHFV35MTf2/xi+Sj29JEY12scjjrzuc22Ku\n" +
                "KmTSsMX2dhaay6LpG6tWg/YkJ8bEEjG44rW4PwIDAQABAoIBABCb03wLNjH1Iy8l\n" +
                "+NgLiz1UO1YE8ciZiHyJe4Sw2eYHEJueJKZh+f2YcsLN6YBw0DgbfTJigwNd+WSW\n" +
                "kQejCeUgjnwFHmZEcUHGgm/626Hw4bge3P89HYOOs4kYx+PZsdC4zxlthixaBoZ2\n" +
                "IDszzPv6KZTcS45F4RRoFHIIvLIf2MQFI0IrKFUqhkjJHSQJHGNn7B5hd0l2cqSr\n" +
                "8c80y+bZxhp4TJwOzIi7j6uta8wsMa0M9WCbr+WULW6CwpL4RdJ7AxTfVWmfhV3y\n" +
                "tLZhTLfQYucPYH/FY+GbD+3np0TZ6SaBy8CSOeq3IZHKN5tl0ZTtVPUnkmc6wKvA\n" +
                "g/xIugkCgYEA597rl6R+5hHbYfX3pNirV++BdBzohlxvn1XCQame5Q2LNUwCZRAf\n" +
                "TChv53V60t1RjWFWC8ct5InYv7BTd6XY5RB8TJi2uwGHpbQGioUBVzCziY6848kW\n" +
                "TPplgkAl3mJ79ovK1Cgz+Ai2zdg7/PHCD/L3k9Nw9qezbfnmYhXRsCMCgYEAzHwG\n" +
                "V/iOeH7UNUY2UuTy4iVCyjMLU2y06hwP2H/HNrB/VyNrWAaxWXsWg7yCY1ZEBzUC\n" +
                "jE8UGt9/AHjPHyCsy/7YixhHyV7Q+Hw82zR/LLww31bXoS2Aa91a2k8+LC00IMhM\n" +
                "nODeX91JePzSwjJUU2X+jrOiKgYwWfMhOnOmSzUCgYB4B9ghqg/e/cN5lKDb/yXD\n" +
                "4cI733MnitZlvzlOUIZmwHod3vkmZBAvlodHZHNukQ9UhtzKQBOryQghQyASQhJj\n" +
                "kgMmi6+cYxBbYTQ5pNyqzBVE0lVJ/aDDvKOrp5Vy57yQRpF6/kzYy3oGWTfEm0oA\n" +
                "g6BSMBS8OtgRyFnQ0Ma5zQKBgA1pDNl0GZPZbX1Y73McRLmEDA9KrbF47Zx03q0q\n" +
                "JBx4EsHaNs39EmB17BITqNFu9OMsHNCq1vy0L5YOVjcKY4q1Weo1W2/fYgEJ1Mvj\n" +
                "c3uxk9rxzQ5q94Mbbzvl+oPXKxYDTNL1BGBxGMJwgjnuJw2ZIMqnCTmMUltz0HgO\n" +
                "4ZcJAoGBAJd7Irbky/URqFyTRBt4FW8Qqyxz/CKTMmu/4gixdOdj+9sPDzKCHufW\n" +
                "Tl+kraRNo3qZPAe9p20swjfhsWnQE4I5Nx/p4Tgc0XIVQFwax9xR6ZtkSwYzod/9\n" +
                "sjyuFx89UsSLFM4brxfwAqvLI9hhydMOIHhPpab5HqPY0xSTsu4S";

//密钥信息用 BASE64 编码加密过，需要先解密
        byte[] decodeKeyinfo = (new BASE64Decoder()).decodeBuffer(keyinfo);
//使用 DerInputStream 读取密钥信息
        DerInputStream dis = new DerInputStream(decodeKeyinfo);
//密钥不含 otherPrimeInfos 信息，故只有 9 段
        DerValue[] ders = dis.getSequence(9);
//依次读取 RSA 因子信息
        int version = ders[0].getBigInteger().intValue();
        BigInteger modulus = ders[1].getBigInteger();
        BigInteger publicExponent = ders[2].getBigInteger();
        BigInteger privateExponent = ders[3].getBigInteger();
        BigInteger primeP = ders[4].getBigInteger();
        BigInteger primeQ = ders[5].getBigInteger();
        BigInteger primeExponentP = ders[6].getBigInteger();
        BigInteger primeExponentQ = ders[7].getBigInteger();
        BigInteger crtCoefficient = ders[8].getBigInteger();
//generate public key and private key
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec rsaPublicKeySpec =
                new RSAPublicKeySpec(modulus, publicExponent);
        PublicKey publicKey = keyFactory.generatePublic(rsaPublicKeySpec);
        RSAPrivateCrtKeySpec rsaPrivateKeySpec =
                new RSAPrivateCrtKeySpec(modulus, publicExponent, privateExponent,
                        primeP, primeQ, primeExponentP, primeExponentQ, crtCoefficient);
        PrivateKey privateKey = keyFactory.generatePrivate(rsaPrivateKeySpec);
        System.out.println(privateKey);
        System.out.println(privateKey.getAlgorithm());

        RsaCrypto.PublicCrypto publicCrypto = new RsaCrypto.PublicCrypto(publicKey);
        RsaCrypto.PrivateCrypto privateCrypto = new RsaCrypto.PrivateCrypto(privateKey);

//        byte[] encrypt = publicCrypto.encrypt("nihao123".getBytes());
//
//
//
//        byte[] decrypt1 = privateCrypto.decrypt(encrypt);
//        System.out.println(new String(decrypt1));

        String data = "QETh7VKnFySI8QZY6O5AI4y1ozfZQsMC98jFhITZmf4wYs8E8IcvISlaTU+J/kK+HlWq+Xt1ShqIvWhnv3V9zsGVF5QXT0eL1GM+90LtbkYCZDMA6qBhZbXcZaps/q+onQubsoqRfD3gAcN8M/JQz4viHPCaeXczIzFZxsdSHuFLHI1ZgsVyhRaAxnPQST8XwH/aUgpjgAuwsnPLrnB8QidFIc1aw16zTtaXKRXsqMyhtdYK+VsOaNv3X0gUU345qYmESCJ73wsau3CWQmLkxmDG5xA1XCIo3GOPF59q0MHMV+FMNJJL7SxAAVLuptZvfjwiHpqx6xyfZuPhx520iA==";
        byte[] decode = Base64.getDecoder().decode(data);
        byte[] decrypt = privateCrypto.decrypt(decode);
        System.out.println(new String(decrypt));
    }
}
