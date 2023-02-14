package com.storage.models.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class WarehouseDto {
    private String uuid;
    private String name;
    private String city;
    private String postcode;
    private String street;
    private float lat;
    private float lng;
}
