package com.storage.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.storage.models.enums.ExtraServices;
import com.storage.models.enums.StorageDuration;
import com.storage.models.enums.StorageSize;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * Object used to send a replay to an
 * enquiry about specific warehouse
 */
@Builder
@Data
public class QuoteDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String postcode;
    private String warehouseName;
    private String city;
    private String street;
    private BigDecimal price;
    private StorageSize storageSize;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    private StorageDuration duration;
    private Set<ExtraServices> extraServices;
}
