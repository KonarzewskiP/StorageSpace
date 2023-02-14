package com.storage.models.requests;

import com.storage.models.enums.SpecType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
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
