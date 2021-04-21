package com.storage.validators;

import com.storage.models.dto.AddressDto;
import com.storage.service.postcodes_api.PostcodeService;
import com.storage.validators.base.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class AddressDtoValidator implements Validator<AddressDto> {

    private final PostcodeService postcodeService;

    @Override
    public Map<String, String> validate(AddressDto address) {
        Map<String, String> errors = new HashMap<>();

        if (isNull(address)) {
            errors.put("AddressDto", "Can not be null");
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

        if (isCityEmpty(address)) {
            errors.put("City", "Can not be empty");
        }
        if (isStreetValid(address)) {
            errors.put("Street", "Can not be empty");
        }
        if (isPostcodeEmpty(address)) {
            errors.put("Postcode", "Can not be empty");
        } else if (!isPostcodeFormatValid(address)) {
            errors.put("Postcode", "Has incorrect format");
        } else if (!isPostcodeExist(address)) {
            errors.put("Postcode", "Does not exist");
        }

        return errors;
    }

    private boolean isCityEmpty(AddressDto address) {
        return address.getCity().isBlank();
    }

    private boolean isStreetValid(AddressDto address) {
        return address.getStreet().isBlank();
    }

    private boolean isPostcodeEmpty(AddressDto address) {
        return address.getPostcode().isBlank();
    }

    private boolean isPostcodeFormatValid(AddressDto address) {
        return address.getPostcode().toUpperCase().matches("(^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$)");
    }

    private boolean isPostcodeExist(AddressDto address) {
        return postcodeService.isValid(address.getPostcode()).getResult();
    }
}
