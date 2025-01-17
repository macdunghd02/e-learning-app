package com.mdd.ela.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author dungmd
 * @created 1/10/2025 2:45 下午
 * @project e-learning-app
 */
@Component
public class JwtUtil {
    @Value("${security.jwt.signer-key}")
    private String SIGNER_KEY;
    private static final String ISSUER = "mdd";

    public String generateAccessToken(String email, long accountId) throws JOSEException {
        // Tạo JWT Claims (các thông tin trong token)
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(email)  // Đặt email làm subject
                .issuer(ISSUER)  // Đặt issuer
                .issueTime(new Date())  // Thời gian phát hành
                .expirationTime(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)))  // Hết hạn sau 1 giờ
                .claim("id", accountId)  // Thêm claim "id"
                .build();

        // Tạo Header với thuật toán HS512
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Tạo JWSObject từ Header và Claims
        JWSObject jwsObject = new JWSObject(header, new Payload(claims.toJSONObject()));

        // Ký token với MACSigner (Sử dụng khóa bí mật)
        jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));

        // Trả về token đã ký
        return jwsObject.serialize();
    }

    /**
     * Tạo Refresh Token
     */
    public String generateRefreshToken(String email, long accountID) throws JOSEException {
        // Tạo JWT Claims cho Refresh Token
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(email)  // Đặt email làm subject
                .issuer(ISSUER)  // Đặt issuer
                .issueTime(new Date())  // Thời gian phát hành
                .expirationTime(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30)))  // Hết hạn sau 30 ngày
                .claim("id",accountID)
                .claim("type", "refreshToken")  // Claim để xác định token là refresh token
                .build();

        // Tạo Header với thuật toán HS512
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        // Tạo JWSObject từ Header và Claims
        JWSObject jwsObject = new JWSObject(header, new Payload(claims.toJSONObject()));

        // Ký token với MACSigner (Sử dụng khóa bí mật)
        jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));

        // Trả về refresh token đã ký
        return jwsObject.serialize();
    }

    /**
     * Kiểm tra tính hợp lệ của token
     */
    public boolean validateToken(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            if (!jwsObject.verify(verifier)) {
                return false;
            }
            // Lấy payload và chuyển đổi sang JSON
            JWTClaimsSet claimsSet = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
            // Kiểm tra thời hạn của token (expiration time)
            if (claimsSet.getExpirationTime().before(new Date(System.currentTimeMillis()))) {
                return false; // Token đã hết hạn
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Lấy thông tin từ token
     */
    public String extractEmail(String token) throws JOSEException, ParseException {
        JWSObject jwsObject = JWSObject.parse(token);
        JWTClaimsSet claimsSet = JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
        return claimsSet.getSubject();  // Trả về email từ claim "subject"
    }
}
