package com.storage.validators;

import com.storage.exceptions.WarehouseServiceException;
import com.storage.models.requests.CreateWarehouseRequest;
import com.storage.validators.base.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class WarehouseCreateRequestValidator implements Validator<CreateWarehouseRequest> {

    @Override
    public Map<String, String> validate(CreateWarehouseRequest request) {
        Map<String, String> errors = new HashMap<>();

        if (isNull(request)) {
            errors.put("WarehouseDto", "Can not be null");
            return errors;
        }
        if (isNull(request.getName())) {
            errors.put("Name", "Can not be null");
            return errors;
        }
        if (isNull(request.getCity())) {
            errors.put("City", "Can not be null");
            return errors;
        }
        if (isNull(request.getStreet())) {
            errors.put("Street", "Can not be null");
            return errors;
        }
        if (isNull(request.getPostcode())) {
            errors.put("Postcode", "Can not be null");
            return errors;
        }

        if (!isNameEmpty(request))
            errors.put("Name", "Can not be empty");
        if (isCityEmpty(request))
            errors.put("City", "Can not be empty");
        if (isStreetValid(request))
            errors.put("Street", "Can not be empty");
        if (isPostcodeEmpty(request))
            errors.put("Postcode", "Can not be empty");
         else if (!isPostcodeFormatValid(request))
            errors.put("Postcode", "Has incorrect format");

        return errors;
    }

    private boolean isNameEmpty(CreateWarehouseRequest warehouse) {
        return !warehouse.getName().isBlank();
    }
    private boolean isCityEmpty(CreateWarehouseRequest warehouse) {
        return warehouse.getCity().isBlank();
    }
    private boolean isStreetValid(CreateWarehouseRequest warehouse) {
        return warehouse.getStreet().isBlank();
    }
    private boolean isPostcodeEmpty(CreateWarehouseRequest warehouse) {
        return warehouse.getPostcode().isBlank();
    }
    private boolean isPostcodeFormatValid(CreateWarehouseRequest warehouse) {
        return warehouse.getPostcode().toUpperCase().matches("(^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$)");
    }


    @Override
    public void throwError(Map<String, String> errors) {
        throw new WarehouseServiceException("Invalid Create Warehouse Request!, errors: " + errors
                .entrySet()
                .stream()
                .map(err -> err.getKey() + " - " + err.getValue())
                .collect(Collectors.joining(",")));
    }
}
