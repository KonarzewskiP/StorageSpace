package com.storage.model.dto;


import com.storage.model.Director;
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
    private String city;
    private String street;
    private String postCode;

}
