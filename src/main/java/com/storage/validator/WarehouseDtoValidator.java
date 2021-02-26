package com.storage.validator;

import com.storage.model.dto.WarehouseDto;
import com.storage.validator.base.Validator;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

@Slf4j
public class WarehouseDtoValidator implements Validator<WarehouseDto> {

    @Override
    public Map<String, String> validate(WarehouseDto warehouse) {
        Map<String, String> errors = new HashMap<>();

        if (!isNameValid(warehouse)) {
            errors.put("Name", "Can not be empty.");
        }
        if (!isCityValid(warehouse)) {
            errors.put("City", "Can not be empty");
        }
        if (!isStreetValid(warehouse)) {
            errors.put("Street", "Can not be empty.");
        }
        if (!isPostcodeValid(warehouse)) {
            errors.put("Postcode","Can not be empty");
        }
        if (!isPostcodeFormatValid(warehouse)) {
            errors.put("Postcode", "Value has incorrect format");
        }
        return errors;
    }

    private boolean isNameValid(WarehouseDto warehouse) {
        return nonNull(warehouse.getName()) && !warehouse.getName().isBlank();
    }

    private boolean isCityValid(WarehouseDto warehouse) {
        return nonNull(warehouse.getCity()) && !warehouse.getCity().isBlank();
    }

    private boolean isStreetValid(WarehouseDto warehouse) {
        return nonNull(warehouse.getStreet()) && !warehouse.getStreet().isBlank();
    }

    private boolean isPostcodeValid(WarehouseDto warehouse) {
        return nonNull(warehouse.getPostCode()) && !warehouse.getPostCode().isBlank();
    }

    private boolean isPostcodeFormatValid(WarehouseDto warehouse) {
        return nonNull(warehouse.getPostCode()) && warehouse.getPostCode().matches("(^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$)");
    }
}
