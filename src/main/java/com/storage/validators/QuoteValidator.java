package com.storage.validators;

import com.storage.models.Quote;
import com.storage.validators.base.Validator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public class QuoteValidator implements Validator<Quote> {

    @Override
    public Map<String, String> validate(Quote quotation) {
        Map<String, String> errors = new HashMap<>();

        if (isNull(quotation)) {
            errors.put("Quotation", "Can not be null");
            return errors;
        }
        if (isNull(quotation.getFirstName())) {
            errors.put("FirstNme", "Can not be null");
            return errors;
        }
        if (isNull(quotation.getWarehouseName())) {
            errors.put("Warehouse", "Can not be null");
            return errors;
        }
        if (isNull(quotation.getSurname())) {
            errors.put("Surname", "Can not be null");
            return errors;
        }
        if (isNull(quotation.getEmail())) {
            errors.put("Email", "Can not be null");
            return errors;
        }
        if (isNull(quotation.getSize())) {
            errors.put("Size", "Can not be null");
            return errors;
        }
        if (isNull(quotation.getType())) {
            errors.put("TypeOfAccount", "Can not be null");
            return errors;
        }
        if (isNull(quotation.getStartDate())) {
            errors.put("StartDate", "Can not be null");
            return errors;
        }
        if (isNull(quotation.getDuration())) {
            errors.put("Duration", "Can not be null");
            return errors;
        }

        if (!isFirstNameEmpty(quotation)) {
            errors.put("FirstNme", "Can not be empty");
        }
        if (!isSurnameEmpty(quotation)) {
            errors.put("Surname", "Can not be empty");
        }
        if (!isWarehouseEmpty(quotation)) {
            errors.put("Warehouse", "Can not be empty");
        }

        if (!isEmailEmpty(quotation)) {
            errors.put("Email", "Can not be empty");
        } else if (!isEmailFormatValid(quotation)) {
            errors.put("Email", "Has incorrect format");
        }

        if (!isStartDateValid(quotation)){
            errors.put("StartDate","Must be after today");
        }

        return errors;
    }

    private boolean isFirstNameEmpty(Quote quotation) {
        return !quotation.getFirstName().isBlank();
    }

    private boolean isSurnameEmpty(Quote quotation) {
        return !quotation.getSurname().isBlank();
    }

    private boolean isWarehouseEmpty(Quote quotation) {
        return !quotation.getWarehouseName().isBlank();
    }

    private boolean isEmailEmpty(Quote quotation) {
        return !quotation.getEmail().isBlank();
    }

    private boolean isStartDateValid(Quote quotation) {
        return quotation.getStartDate().isAfter(LocalDate.now());
    }

    private boolean isEmailFormatValid(Quote quotation) {
        var pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher(quotation.getEmail());
        return matcher.find();
    }
}
