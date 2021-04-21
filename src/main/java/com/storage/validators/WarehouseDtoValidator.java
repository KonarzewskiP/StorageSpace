package com.storage.validators;

import com.storage.models.dto.WarehouseDto;
import com.storage.validators.base.Validator;

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

        if (!isNameEmpty(warehouse)) {
            errors.put("Name", "Can not be empty");
        }

        return errors;
    }

    private boolean isNameEmpty(WarehouseDto warehouse) {
        return !warehouse.getName().isBlank();
    }


}
