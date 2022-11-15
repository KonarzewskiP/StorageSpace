package com.storage.validators;

import com.storage.models.dto.StorageRoomDto;
import com.storage.models.requests.StorageRoomUpdateRequest;
import com.storage.validators.base.Validator;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

//TODO StorageRoom
public class StorageRoomUpdateReqValidator implements Validator<StorageRoomUpdateRequest> {

    @Override
    public Map<String, String> validate(StorageRoomUpdateRequest storageRoom) {
        Map<String, String> errors = new HashMap<>();

        if (isNull(storageRoom)) {
            errors.put("Update request", "Can not be null");
            return errors;
        }

        if (isNull(storageRoom.getReserved())) {
            errors.put("Reserved", "Can not be null");
            return errors;
        }

//        if (isStorageNotReserved(storageRoom)){
//            return errors;
//        }


//        if (isNull(storageRoom.getStartDate())){
//            errors.put("StartDate", "Can not be null");
//            return errors;
//
//        } else if (isNull(storageRoom.getEndDate())){
//            errors.put("EndDate", "Can not be null");
//            return errors;
//        }

        if (!isStartDateAfterEndDate(storageRoom)){
            errors.put("Invalid Date","Start date must be before end date");
        } else if (!isStartDateAndEndDateTheSame(storageRoom)) {
            errors.put("Invalid Date","Start and end date is the same");
        }

        return errors;
    }

    private boolean isStorageNotReserved(StorageRoomDto storageRoomDto) {
        return !storageRoomDto.getReserved();
    }

    private boolean isStartDateAfterEndDate(StorageRoomDto storageRoomDto) {
       return !storageRoomDto.getStartDate().isAfter(storageRoomDto.getEndDate());
    }

    private boolean isStartDateAndEndDateTheSame(StorageRoomDto storageRoomDto) {
        return !storageRoomDto.getStartDate().isEqual(storageRoomDto.getEndDate());
    }

}
