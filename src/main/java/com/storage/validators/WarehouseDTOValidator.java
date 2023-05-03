package com.storage.validators;

import com.storage.exceptions.ObjectValidationException;
import com.storage.models.requests.CreateWarehouseRequest;
import com.storage.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class WarehouseDTOValidator {
    public static void validate(CreateWarehouseRequest request) {
        Map<String, String> errors = new HashMap<>();

        if (isNull(request))
            throw new ObjectValidationException("CreateWarehouseRequest can not be null");

        if (StringUtils.isBlank(request.name()))
            errors.put("Name", "Can not be null or empty");
        if (StringUtils.isBlank(request.city()))
            errors.put("City", "Can not be null or empty");
        if (StringUtils.isBlank(request.street()))
            errors.put("Street", "Can not be null or empty");
        if (StringUtils.isBlank(request.postcode()))
            errors.put("Postcode", "Can not be null or empty");
        if (!StringUtils.isPostcodeFormatValid(request.postcode()))
            errors.put("Postcode", "Has incorrect format");

        if (!errors.isEmpty()) {
            throw new ObjectValidationException("Invalid Create Warehouse Request!, errors: " + errors
                    .entrySet()
                    .stream()
                    .map(err -> err.getKey() + " - " + err.getValue())
                    .collect(Collectors.joining(",")));
        }
    }

}
