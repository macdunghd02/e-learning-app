package com.mdd.ela.service.base;

/**
 * @author dungmd
 * @created 1/12/2025 5:55 下午
 * @project e-learning-app
 */

public interface BaseMailService {
    void sendOtpEmail(String email, String otp);
}
