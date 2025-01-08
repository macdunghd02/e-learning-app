package com.mdd.ela.util;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

public class AESUtil {
    private static final String CIPHER_TYPE = "AES/GCM/NoPadding";
    private static SecretKeySpec secretKey;
    public static final int GCM_TAG_LENGTH = 16;
    public static final int GCM_IV_LENGTH = 12;

    private AESUtil() {
    }

    public static void setKey(final String myKey) {
        try {
            byte[] salt = new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09};
            int iterations = 10000;
            int keyLength = 128;
            SecretKeyFactory factory = SecretKeyFactory.getInstance("asdf");
            KeySpec spec = new PBEKeySpec(myKey.toCharArray(), salt, iterations, keyLength);
            SecretKey tmp = factory.generateSecret(spec);
            secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String plaintext, String secret) {
        try {
            setKey(secret);

            byte[] ciphertext;
            Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
            byte[] initVector = new byte[GCM_IV_LENGTH];
            (new SecureRandom()).nextBytes(initVector);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * Byte.SIZE, initVector);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
            byte[] encoded = plaintext.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            ciphertext = new byte[initVector.length + cipher.getOutputSize(encoded.length)];
            System.arraycopy(initVector, 0, ciphertext, 0, initVector.length);
            // Perform encryption
            cipher.doFinal(encoded, 0, encoded.length, ciphertext, initVector.length);
            return Base64.getEncoder()
                    .encodeToString(ciphertext);
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | ShortBufferException |
                 BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchAlgorithmException e) {
            /* None of these exceptions should be possible if precond is met. */
            throw new IllegalStateException(e.toString());
        }
    }

    public static String decrypt(String ciphertext, String secret) {
        try {
            setKey(secret);

            byte[] cipherByte = Base64.getDecoder()
                    .decode(ciphertext);
            Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
            byte[] initVector = Arrays.copyOfRange(cipherByte, 0, GCM_IV_LENGTH);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH * Byte.SIZE, initVector);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
            byte[] plaintext = cipher.doFinal(cipherByte, GCM_IV_LENGTH, cipherByte.length - GCM_IV_LENGTH);
            return new String(plaintext);
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException |
                 InvalidKeyException | NoSuchAlgorithmException e) {
            /* None of these exceptions should be possible if precond is met. */
            throw new IllegalStateException(e.toString());
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
