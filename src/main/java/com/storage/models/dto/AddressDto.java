package com.storage.models.dto;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class AddressDto {
    private String city;
    private String postcode;
    private String street;
}
