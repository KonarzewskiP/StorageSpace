package com.storage.utils;

import com.storage.exceptions.BadRequestException;
import com.storage.exceptions.EmailException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static boolean isEmailFormatValid(String email) {
        if (isBlank(email))
            throw new EmailException("Email can not be empty!");

        var pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static boolean isPostcodeFormatValid(String postcode) {
        if (isBlank(postcode))
            throw new BadRequestException("Postcode can not be empty or null");

        Pattern pattern = Pattern.compile("^[A-Z]{1,2}[0-9R][0-9A-Z]? ?[0-9][ABD-HJLNP-UW-Z]{2}$");
        Matcher matcher = pattern.matcher(postcode);

        return matcher.find();
    }

}
