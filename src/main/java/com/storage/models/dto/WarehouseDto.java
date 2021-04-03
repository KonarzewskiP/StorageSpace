package com.storage.models.dto;


import com.storage.models.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseDto {

    private Long id;
    private String name;
    private Address address;

}
