package com.storage.models.requests;

import com.storage.exceptions.BadRequestException;
import com.storage.models.enums.ExtraServices;
import com.storage.models.enums.StorageDuration;
import com.storage.models.enums.StorageSize;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

import static com.storage.utils.StringUtils.isEmailFormatValid;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Data
public class QuoteEstimateRequest {
    private String warehouseUuid;
    private LocalDate startDate;
    private String firstName;
    private String lastName;
    private String email;
    private StorageSize storageSize;
    private StorageDuration duration;
    private Set<ExtraServices> extraServices;

    public void isValid() {
        if (isBlank(warehouseUuid))
            throw new BadRequestException("Quote Estimate Request is mandatory!");
        if (startDate == null)
            throw new BadRequestException("Start At is mandatory!");
        if (isBlank(firstName))
            throw new BadRequestException("First Name is mandatory!");
        if (isBlank(lastName))
            throw new BadRequestException("Last Name is mandatory!");
        if (storageSize == null)
            throw new BadRequestException("Storage Size is mandatory!");
        if (duration == null)
            throw new BadRequestException("Duration is mandatory!");

        isEmailFormatValid(email);
    }
}
