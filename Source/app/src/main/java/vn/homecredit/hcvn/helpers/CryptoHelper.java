/*
 * Copyright (c) 2018 Home Credit Vietnam. All rights reserved.
 *
 * Last modified 7/4/18 5:21 PM, by Hien.NM
 */

package vn.homecredit.hcvn.helpers;

import android.os.Build;
import android.support.annotation.RequiresApi;
import com.google.gson.Gson;
import vn.homecredit.hcvn.helpers.entities.Asn1Object;
import vn.homecredit.hcvn.helpers.entities.DerParser;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public final class CryptoHelper {

    private static final String Key = "11223344556677889900AABBCCDDEEFF";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encrypt(String value) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(Key.getBytes("US-ASCII"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/ISO10126PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decrypt(String encrypted) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(Key.getBytes("US-ASCII"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/ISO10126Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encryptObject(Object obj) {
        try {
            Gson gson = new Gson();
            String result = gson.toJson(obj);
            return encrypt(result);
        } catch (Exception ex) {
            return "";
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static <T> T decryptObject(String enscriptedObject, Class<T> type) {
        try {
            String jsonString = decrypt(enscriptedObject);
            Gson gson = new Gson();
            return gson.fromJson(jsonString, type);
        } catch (Exception ex) {
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String signWithRSAPem(String privateKeyString, String message) {
        try {
            PrivateKey privateKey = getPrivateKeyFromString(privateKeyString);
            Signature sign = Signature.getInstance("SHA1withRSA");
            sign.initSign(privateKey);
            sign.update(message.getBytes("UTF-8"));

            return new String(Base64.getEncoder().encodeToString(sign.sign()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String signWithRSAPem(String message) {

        String privateKeyString = "-----BEGIN RSA PRIVATE KEY-----" +
                "MIIEowIBAAKCAQEAwKlnxuWhZh05235ZW/D1+OB/a3EBHTfhN7e5pVdvYBQtNOZVnbQKC" +
                "DsZUGx4PNVmklRlq4MiWqsSDWhuiwXSsY9vQenRHf/Qi/UVVZO+/npoMiwr/afQi7MygE" +
                "gXRvtfTE5GF9LeetZ0yog51p3jT2hjZ1keVMKHEQMk6hGc1Dr5iq0xfqeQeVdTxabIp7I" +
                "FsHgb5l5Wtu6ddy0ceQ+Jrh77DVldNLnA7KExFl3kowhosmRdT5U51CMl6T5ftbQyMgPX" +
                "RSoq693VHQ0QnXav9BDtc4+LzJMp4+kmxlW9FlTY+VGwwvYLV4ArWd/AJBxaNz4NFlSmm" +
                "ncjFgDa6lOMswIDAQABAoIBABs9uMU4QcZw+nXNGzBzVSwv/gRAkbJCbO9WZIL65m4U3E" +
                "F/Efr+8m4wgQdMlwGvANZO5aNHIuvkBWTs77hrhduCVu5gc6eEvMWVTUNw5RARWbtCYKP" +
                "4fvXJzCm0R8pLD7H33VRQBhIjUZczhM4SPApFzcxWnwHIEZX3tlf8Tor5XmtVDOXX4Rd1" +
                "kLNIm1V0XHL4Gx9OM8JHHOAlQJ1q3vqr3NlMo/StZZJSmEWxl+tH1kbZ4kKfEMvHHFyiE" +
                "sbHjX8OyoGN8wmJTl5JIdbTqzYfPN3sN60FemrKrNhKvEWVDgt7vzuFPTkKzih+9vAaUz" +
                "UmVw68uKqcH6tMuwXvsikCgYEA/NSjw0aOyFK7D7aXQAUqz4Fp2QRB2SzWnGKHunEkK1d" +
                "UhWmnfktVb2LaS5btajX1uscJLoTZZVuXjFI373C0tR9VoM6ia1Q6gVrBEHrGB+5gDkKY" +
                "rK33xrr3kzZiIkwUXwfk/Fjj83b7eshjCUFPgI/rJl95A0MuPqWlsDF5hakCgYEAwxOtZ" +
                "pHOTYkYHE8B3oYNxMg6jsL3phh0xPjn1L6upgdCQsZWrsqiSpyZiAQT0PENDknxzH8/2F" +
                "C163Q7Q0Gh3nBsOa+NaMbbF/yaNRiwaBm8OgI0T1hemN8737OZ6e5PIO4EvZtGKYzWUfe" +
                "a0K/ntVNaIjSiG9aQuiGVDJAxgPsCgYBvnlzSCCNfEw8ch8rMa7uTspmNQpDjlSUGE4XC" +
                "bNLAA6T/5T4Zxp5oN2cSOgaEfmAG3noSPHpRghcsztt87PAKilPeU3tnEN9nXi6f3OPye" +
                "m2TvhjzGzapWL+WEUtPwvC3Z5/TPFcelCe/9Bms61Vy95aj8UBWjWEsLSsx1xXESQKBgB" +
                "pg6Mu736fAaBA75sbOXcJESRbQ6mf9WpzgA4lTF46/4IfjBgmwwO1R67jmxD82uUs7WIU" +
                "Ml/eKQm9KUlEv6NGJZjkOshJ/AVVnBPs1EXWiNT+q2KP6ESCe3xvKz39AiFatmNqCyqQH" +
                "tVt0He0mPDDayo9hiqI1C3D/5YXDYc5NAoGBAO1Kuwukb//9jMZlIThkKgqIbYTktkQko" +
                "6drs0esXozBYz1wPrXiRJoHwsaz8mf0CWUMaizsdOLWSG5HlC+H/+TmAZvj4Bf2qClFAM" +
                "7NwEq+DpL69pmMst1og/XHhTm0fTJuIjSYNLSZhhbSXPTqBppH/+HKgdIOZb45tornezN" +
                "x" +
                "-----END RSA PRIVATE KEY-----";
        return signWithRSAPem(privateKeyString, message);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean verifyRSASignedString(String publicKeyString, String message, String signature) {
        try {
            PublicKey publicKey = getPublicKeyFromString(publicKeyString);
            Signature sign = Signature.getInstance("SHA1withRSA");
            sign.initVerify(publicKey);
            sign.update(message.getBytes("UTF-8"));
            return sign.verify(Base64.getDecoder().decode(signature.getBytes("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static RSAPrivateKey getPrivateKeyFromString(String key) throws IOException, GeneralSecurityException {
        String privateKeyPEM = key;

        // Remove the first and last lines
        privateKeyPEM = privateKeyPEM.replace("-----BEGIN RSA PRIVATE KEY-----", "");
        privateKeyPEM = privateKeyPEM.replace("-----END RSA PRIVATE KEY-----", "");
        privateKeyPEM = privateKeyPEM.trim();

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
        KeySpec keySpec = getRSAKeySpec(encoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");

        return (RSAPrivateKey) kf.generatePrivate(keySpec);
    }

    private static RSAPrivateCrtKeySpec getRSAKeySpec(byte[] keyBytes) throws IOException {
        DerParser parser = new DerParser(keyBytes);
        Asn1Object sequence = parser.read();

        if (sequence.getType() != DerParser.SEQUENCE)
            throw new IOException("Invalid DER: not a sequence"); //$NON-NLS-1$

        // Parse inside the sequence
        parser = sequence.getParser();

        parser.read(); // Skip version
        BigInteger modulus = parser.read().getInteger();
        BigInteger publicExp = parser.read().getInteger();
        BigInteger privateExp = parser.read().getInteger();
        BigInteger prime1 = parser.read().getInteger();
        BigInteger prime2 = parser.read().getInteger();
        BigInteger exp1 = parser.read().getInteger();
        BigInteger exp2 = parser.read().getInteger();
        BigInteger crtCoef = parser.read().getInteger();

        RSAPrivateCrtKeySpec keySpec = new RSAPrivateCrtKeySpec(
                modulus, publicExp, privateExp, prime1, prime2,
                exp1, exp2, crtCoef);

        return keySpec;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static RSAPublicKey getPublicKeyFromString(String key) throws GeneralSecurityException {
        String publicKeyPEM = key;

        // Remove the first and last lines
        publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "");
        publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");
        publicKeyPEM = publicKeyPEM.trim();

        // Base64 decode data
        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);

        KeyFactory kf = KeyFactory.getInstance("RSA");
        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(encoded));
        return pubKey;
    }
}


