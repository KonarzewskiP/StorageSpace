package com.storage.validator;

import com.storage.model.dto.WarehouseDto;
import com.storage.validator.base.Validator;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class WarehouseDtoValidator implements Validator<WarehouseDto> {

    @Override
    public Map<String, String> validate(WarehouseDto warehouse) {
        Map<String, String> errors = new HashMap<>();

        if (isNull(warehouse)) {
            errors.put("WarehouseDto", "Can not be null");
            return errors;
        }
        if (isNull(warehouse.getName())) {
            errors.put("Name", "Can not be null");
            return errors;
        }
        if (isNull(warehouse.getCity())) {
            errors.put("City", "Can not be null");
            return errors;
        }
        if (isNull(warehouse.getStreet())) {
            errors.put("Street", "Can not be null");
            return errors;
        }
        if (isNull(warehouse.getPostCode())) {
            errors.put("Postcode", "Can not be null");
            return errors;
        }

        if (!isNameEmpty(warehouse)) {
            errors.put("Name", "Can not be empty");
        }
        if (!isCityEmpty(warehouse)) {
            errors.put("City", "Can not be empty");
        }
        if (!isStreetValid(warehouse)) {
            errors.put("Street", "Can not be empty");
        }
        if (!isPostcodeEmpty(warehouse)) {
            errors.put("Postcode", "Can not be empty");
        } else if (!isPostcodeFormatValid(warehouse)) {
            errors.put("Postcode", "Value has incorrect format");
        }
        return errors;
    }

    private boolean isNameEmpty(WarehouseDto warehouse) {
        return !warehouse.getName().isBlank();
    }

    private boolean isCityEmpty(WarehouseDto warehouse) {
        return !warehouse.getCity().isBlank();
    }

    private boolean isStreetValid(WarehouseDto warehouse) {
        return !warehouse.getStreet().isBlank();
    }

    private boolean isPostcodeEmpty(WarehouseDto warehouse) {
        return !warehouse.getPostCode().isBlank();
    }

    private boolean isPostcodeFormatValid(WarehouseDto warehouse) {
        return warehouse.getPostCode().matches("(^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$)");
    }
}
