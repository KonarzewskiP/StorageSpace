package com.storage.models.requests;

import com.storage.models.enums.SpecType;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class CreateWarehouseRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String city;
    @NotBlank
    private String postcode;
    @NotBlank
    private String street;
    private SpecType specType;
    private BigDecimal totalRentalAreaInM2;
}
