package com.storage.models.requests;

import com.storage.models.enums.ExtraServices;
import com.storage.models.enums.StorageDuration;
import com.storage.models.enums.StorageSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

import static com.storage.utils.StringUtils.isEmailFormatValid;

@Value
public class QuoteEstimateRequest {
    @NotBlank
    String warehouseUuid;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate startDate;
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    @NotBlank
    String email;
    @NotNull
    StorageSize storageSize;
    @NotNull
    StorageDuration duration;
    Set<ExtraServices> extraServices;

    public void isValid() {
        isEmailFormatValid(email);
    }
}
