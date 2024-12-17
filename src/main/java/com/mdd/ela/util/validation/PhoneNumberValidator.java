package com.mdd.ela.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberPattern, String> {
    private static final String PHONE_PATTERN = "^[+\\d]+(?:[\\s\\d]+)*$";
    private static final Pattern PATTERN = Pattern.compile(PHONE_PATTERN);
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        if(phone != null && !phone.isEmpty()) return (validate(phone));
        return true;
    }
    private static boolean validate(final String phone) {
        Matcher matcher = PATTERN.matcher(phone);
        return matcher.matches();
    }
}
