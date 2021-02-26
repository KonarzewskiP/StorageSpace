package com.storage.builders;

import com.storage.model.Director;
import com.storage.model.Warehouse;
import com.storage.model.dto.DirectorDto;
import com.storage.model.dto.WarehouseDto;
import com.storage.model.enums.Gender;

public interface MockDataForTest {

    static Director createDirector(){
        return Director.builder()
                .id(1L)
                .firstName("Veronica")
                .lastName("Jobs")
                .gender(Gender.FEMALE)

                .build();
    }

    static DirectorDto createDirectorDto(){
        return DirectorDto.builder()
                .id(1L)
                .firstName("Veronica")
                .lastName("Jobs")
                .gender(Gender.FEMALE)
                .build();
    }

    static Warehouse createWarehouse(){
        return Warehouse.builder()
                .id(2L)
                .name("Big Yellow")
                .city("London")
                .street("289 Kennington Ln")
                .postCode("SE11 5QY")
                .director(createDirector())
                .build();
    }

    static WarehouseDto createWarehouseDto(){
        return WarehouseDto.builder()
                .id(2L)
                .name("Big Yellow")
                .city("London")
                .street("289 Kennington Ln")
                .postCode("SE11 5QY")
                .build();
    }
}
