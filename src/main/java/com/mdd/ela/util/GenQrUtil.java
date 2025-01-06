package com.mdd.ela.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mdd.ela.exception.ElaRuntimeException;
import com.mdd.ela.util.constants.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author TruongVD
 * @project config-ticket-service
 * @date 14/03/2024
 */
public class GenQrUtil {
    private GenQrUtil() {
    }
    public static String genQR(String input) throws IOException, WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        String qrCodeData = AESUtil.encrypt(input, Constants.KEY_ENCRYPT);
        ByteArrayOutputStream qrCodeStream = new ByteArrayOutputStream();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, 200, 200, hints);
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", qrCodeStream);
        return Base64.getEncoder().encodeToString(qrCodeStream.toByteArray());
    }

    public static String genQRForTicket(String qrCodeData) throws IOException, WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        ByteArrayOutputStream qrCodeStream = new ByteArrayOutputStream();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, 200, 200, hints);
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", qrCodeStream);
        return Base64.getEncoder().encodeToString(qrCodeStream.toByteArray());
    }

    public static String genQRTicketNumber(String hex3B) {
        Pattern HEXADECIMAL_PATTERN = Pattern.compile("\\p{XDigit}+");
        if (hex3B.length() != 6 || !HEXADECIMAL_PATTERN.matcher(hex3B).matches()) {
            throw new ElaRuntimeException("invalidHex3B");
        }
        List<String> split = Arrays.asList(hex3B.split("(?<=\\G.{2})"));
        Collections.reverse(split);
        return String.valueOf(Long.parseLong(String.join("", split), 16)); // hex3B > reversed > dec
    }
}
