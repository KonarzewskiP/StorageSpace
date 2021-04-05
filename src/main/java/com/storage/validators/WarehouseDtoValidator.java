package com.storage.validators;

import com.storage.models.Address;
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

        var address = warehouse.getAddress();

        if (isNull(address)) {
            errors.put("Address", "Can not be null");
            return errors;
        }

        if (isNull(warehouse.getName())) {
            errors.put("Name", "Can not be null");
            return errors;
        }
        if (isNull(address.getCity())) {
            errors.put("City", "Can not be null");
            return errors;
        }
        if (isNull(address.getStreet())) {
            errors.put("Street", "Can not be null");
            return errors;
        }
        if (isNull(address.getPostcode())) {
            errors.put("Postcode", "Can not be null");
            return errors;
        }

        if (!isNameEmpty(warehouse)) {
            errors.put("Name", "Can not be empty");
        }
        if (!isCityEmpty(address)) {
            errors.put("City", "Can not be empty");
        }
        if (!isStreetValid(address)) {
            errors.put("Street", "Can not be empty");
        }
        if (!isPostcodeEmpty(address)) {
            errors.put("Postcode", "Can not be empty");
        } else if (!isPostcodeFormatValid(address)) {
            errors.put("Postcode", "Has incorrect format");
        }
        return errors;
    }

    private boolean isNameEmpty(WarehouseDto warehouse) {
        return !warehouse.getName().isBlank();
    }

    private boolean isCityEmpty(Address address) {
        return !address.getCity().isBlank();
    }

    private boolean isStreetValid(Address address) {
        return !address.getStreet().isBlank();
    }

    private boolean isPostcodeEmpty(Address address) {
        return !address.getPostcode().isBlank();
    }

    private boolean isPostcodeFormatValid(Address address) {
        return address.getPostcode().toUpperCase().matches("(^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$)");
    }
}
