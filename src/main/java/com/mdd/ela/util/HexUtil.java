package com.mdd.ela.util;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HexUtil {
//    private static final char[] HEX_ARRAY_CHAR = "0123456789abcdef".toCharArray();
//    public static String bytesToHex(byte[] bytes) {
//        char[] hexChars = new char[bytes.length * 2];
//        for (int j = 0; j < bytes.length; j++) {
//            int v = bytes[j] & 0xFF;
//            hexChars[j * 2] = HEX_ARRAY_CHAR[v >>> 4];
//            hexChars[j * 2 + 1] = HEX_ARRAY_CHAR[v & 0x0F];
//        }
//        return new String(hexChars);
//    }

    private static final byte[] HEX_ARRAY = "0123456789abcdef".getBytes(StandardCharsets.US_ASCII);

    public static String bytesArrayToHexString(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static String decToHex(String dec) {
        return String.format("%1$02x", Long.parseLong(dec));
    }
    public static String hexToDec(String hex) {
        return String.valueOf(Long.parseLong(hex, 16));
    }

    public static String genInit3BHexForQR() {
        // not reversed yet
        Random rand = new Random();
        StringBuilder qrCodeData = new StringBuilder(); // hex: "xx xx xx" (first x is random 0 -> 3)
        List<String> firstHexForQRTicket = Arrays.asList("0", "1", "2", "3");
        List<String> hex = Arrays.asList("0123456789abcdef".split(""));
        qrCodeData.append(firstHexForQRTicket.get(rand.nextInt(firstHexForQRTicket.size())));
        for (int i = 0; i < 5; i++) {
            qrCodeData.append(hex.get(rand.nextInt(hex.size())));
        }
        return qrCodeData.toString();
    }


    public static String convertReversedDecToDec(String reverseDec) {
        Long reverseNum = Long.parseLong(reverseDec, 10);
        Long num = ((reverseNum>>24)&0xffL) |       // byte 3 to byte 0
                ((reverseNum<<8)&0xff0000L) |    // byte 1 to byte 2
                ((reverseNum>>8)&0xff00L) |      // byte 2 to byte 1
                ((reverseNum<<24)&0xff000000L);  // byte 0 to byte 3
        return num.toString();
    }

}
