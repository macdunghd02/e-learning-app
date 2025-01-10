package com.mdd.ela.util;


import com.mdd.ela.dto.response.ErrorExcelDto;
import com.mdd.ela.exception.AppRuntimeException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.Getter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ValidationUtil {

    private static final String DATE_FORMAT = "dd-MM-yyyy";

    @Getter
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private ValidationUtil() {
    }

    /**
     * <p>Validating fields of object that attached with javax.validation.constraints annotations</p>
     *
     * @param object
     * @return
     * @des
     */
    public static Map<String, Object> validateObject(Object object) {
        Map<String, Object> errMap = new HashMap<>();
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        for (ConstraintViolation<Object> violation : violations
        ) {
            errMap.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return errMap;
    }



    public static Map<String, String> validateProperty(Class clazz, String propertyName, Object inputValue) {
        Map<String, String> errMap = new HashMap<>();
        Set<ConstraintViolation<Object>> violations = validator.validateValue(clazz, propertyName, inputValue);
        for (ConstraintViolation<Object> violation : violations
        ) {
            errMap.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return errMap;
    }


    public static ErrorExcelDto validateObjectExcel(Object object) {
        ErrorExcelDto errorExcel = new ErrorExcelDto();
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            errorExcel.addError(violations.iterator().next().getMessage());
        }
        return errorExcel;
    }


    public static boolean isIpAddress(String ipAddress){
        String regex = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }

    public static boolean isMacAddress(String macAddress){
        String regex = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})|([0-9a-fA-F]{4}\\.[0-9a-fA-F]{4}\\.[0-9a-fA-F]{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(macAddress);
        return matcher.matches();
    }

    public static void compareTo(String stringDateFrom, String stringDateTo) {
        try {
            Date dateFrom = new SimpleDateFormat(DATE_FORMAT).parse(stringDateFrom);
            Date dateTo = new SimpleDateFormat(DATE_FORMAT).parse(stringDateTo);
            if (dateTo.compareTo(dateFrom) <= 0) {
                throw new AppRuntimeException("duplicatedPeriod");
            }
        } catch (ParseException e) {
            throw new AppRuntimeException("dateInvalidFormat");
        }
    }

    public static void validateDateFromDateTo(String dateFrom, String dateTo){
        Date startDate;
        Date endDate;
        try {
            startDate = new SimpleDateFormat(DATE_FORMAT).parse(dateFrom);
            endDate = new SimpleDateFormat(DATE_FORMAT).parse(dateTo);
        }catch (Exception e){
            throw new AppRuntimeException("dateInvalidFormat");
        }
        if(startDate.after(endDate)){
            throw new AppRuntimeException("endDateMustBeAfterStartDate");
        }
    }
    public static void validateRoomLevelCode(String roomLevelCode){
        String regex = "[^@#$%]+";
        if(!roomLevelCode.matches(regex)){
            throw new AppRuntimeException("invalidCode");
        }
    }

    public static void validateStringCode(String code){
        String regex = "[a-zA-Z0-9 _-]+";
        if(!code.matches(regex)){
            throw new AppRuntimeException("stringInputOnlyContainsLettersNumbersAndUnderscore");
        }
    }

    public static String validateStringDate(String dateString){
        if(null == dateString){
            return null;
        }
        try{
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            Date date = new SimpleDateFormat(DATE_FORMAT).parse(dateString);
            return df.format(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date getDateFromStringDate(String dateString){
        if(null == dateString){
            throw new AppRuntimeException("dateMustNotBeNull");
        }
        try{
            return new SimpleDateFormat(DATE_FORMAT).parse(dateString);
        } catch (ParseException e) {
            throw new AppRuntimeException("dateInvalidFormat");
        }
    }

}