package com.storage.models.requests;

import com.storage.exceptions.BadRequestException;
import com.storage.models.enums.ExtraServices;
import com.storage.models.enums.Reason;
import com.storage.models.enums.StorageDuration;
import com.storage.models.enums.StorageSize;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

import static com.storage.utils.StringUtils.isEmailFormatValid;

@Data
public class QuoteEstimateRequest {
    private String warehouseUuid;
    private LocalDate startAt;
    private String firstName;
    private String lastName;
    private String email;
    private Reason reason;
    private StorageSize storageSize;
    private StorageDuration duration;
    private Set<ExtraServices> extraServices;

    public void isValid() {
        if (warehouseUuid == null || warehouseUuid.length() == 0)
            throw new BadRequestException("Quote Estimate Request is mandatory!");
        if (startAt == null)
            throw new BadRequestException("Start At is mandatory!");
        if (firstName == null || firstName.length() == 0)
            throw new BadRequestException("First Name is mandatory!");
        if (lastName == null || lastName.length() == 0)
            throw new BadRequestException("Last Name is mandatory!");
        if (reason == null)
            throw new BadRequestException("Reason is mandatory!");
        if (storageSize == null)
            throw new BadRequestException("Storage Size is mandatory!");
        if (duration == null)
            throw new BadRequestException("Duration is mandatory!");

        isEmailFormatValid(email);
    }
}
