package com.storage.models.requests;

import com.storage.models.enums.ExtraServices;
import com.storage.models.enums.StorageDuration;
import com.storage.models.enums.StorageSize;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record QuoteEstimateRequest(
        String warehouseUuid,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate startDate,
        String firstName,
        String lastName,
        String email,
        StorageSize storageSize,
        StorageDuration duration,
        Set<ExtraServices> extraServices
) {
}
