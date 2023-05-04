package com.storage.validators;

import com.storage.exceptions.ObjectValidationException;
import com.storage.models.requests.QuoteEstimateRequest;
import com.storage.utils.StringUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class QuoteValidator {

    public static void validate(QuoteEstimateRequest request) {
        Map<String, String> errors = new HashMap<>();

        if (isNull(request))
            throw new ObjectValidationException("QuoteEstimateRequest can not be null");

        if (StringUtils.isBlank(request.warehouseUuid()))
            errors.put("WarehouseUuid", "Can not be null or empty");
        if (StringUtils.isBlank(request.lastName()))
            errors.put("LastName", "Can not be null or empty");
        if (StringUtils.isBlank(request.firstName()))
            errors.put("FirstName", "Can not be null or empty");
        if (StringUtils.isBlank(request.email()))
            errors.put("Email", "Can not be null or empty");


        if (isNull(request.storageSize()))
            errors.put("Size", "Can not be null");
        if (isNull(request.startDate()))
            errors.put("StartDate", "Can not be null");
        if (isNull(request.duration()))
            errors.put("Duration", "Can not be null");

        if (!StringUtils.isEmailFormatValid(request.email()))
            errors.put("Email", "Has incorrect format. Email: " + request.email());

        if (!isStartDateValid(request.startDate())) {
            errors.put("StartDate", "Must be after today");
        }

        if (!errors.isEmpty()) {
            throw new ObjectValidationException("Invalid QuoteEstimateRequest!, errors: " + errors
                    .entrySet()
                    .stream()
                    .map(err -> err.getKey() + " -> " + err.getValue())
                    .collect(Collectors.joining(", ")));
        }
    }

    private static boolean isStartDateValid(LocalDate startDate) {
        return startDate.isAfter(LocalDate.now());
    }


}
