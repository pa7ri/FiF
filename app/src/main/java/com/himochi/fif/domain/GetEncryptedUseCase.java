package com.himochi.fif.domain;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GetEncryptedUseCase {
    public String getEncripted(String s, String alg) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance(alg);
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            return bytesToHex(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            Log.e("ERROR BUILDING ALG", e.getMessage());
        }
        return "";
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
