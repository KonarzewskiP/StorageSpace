package com.storage.utils;

import com.storage.exceptions.EmailException;

import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static void isEmailFormatValid(String email) {
        if (isBlank(email))
            throw new EmailException("Email can not be empty!");

        var pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher(email);

        if (!matcher.find())
            throw new EmailException(String.format("Email [%s] has invalid format!", email));
    }


}
