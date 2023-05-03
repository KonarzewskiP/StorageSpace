package com.storage.utils.mapper;

import com.storage.models.Warehouse;
import com.storage.models.dto.WarehouseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WarehouseMapper {
    private ModelMapper modelMapper;

    public WarehouseMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public WarehouseDto map(Warehouse warehouse) {
        return modelMapper.map(warehouse, WarehouseDto.class);
    }
}
