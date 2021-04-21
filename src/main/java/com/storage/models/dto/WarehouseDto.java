package com.storage.models.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class WarehouseDto {

    private Long id;
    private String name;
    private AddressDto address;
}
