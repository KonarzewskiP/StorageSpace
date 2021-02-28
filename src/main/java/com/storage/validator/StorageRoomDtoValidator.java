package com.storage.validator;

import com.storage.model.dto.StorageRoomDto;
import com.storage.validator.base.Validator;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class StorageRoomDtoValidator implements Validator<StorageRoomDto> {

    @Override
    public Map<String, String> validate(StorageRoomDto storageRoom) {
        Map<String, String> errors = new HashMap<>();

        if (isNull(storageRoom)) {
            errors.put("StorageRoomDto", "Can not be null");
            return errors;
        }
        if (isNull(storageRoom.getSize())) {
            errors.put("Size", "Can not be null");
            return errors;
        }
        if (isNull(storageRoom.getReserved())) {
            errors.put("Reserved", "Can not be null");
            return errors;
        }

        return errors;
    }
}
