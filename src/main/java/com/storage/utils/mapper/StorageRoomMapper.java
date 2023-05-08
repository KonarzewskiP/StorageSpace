
package com.storage.utils.mapper;

import com.storage.models.StorageRoom;
import com.storage.models.dto.StorageRoomDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StorageRoomMapper {
    private final ModelMapper modelMapper;

    public StorageRoomMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public StorageRoomDto map(StorageRoom storageRoom) {
        return modelMapper.map(storageRoom, StorageRoomDto.class);
    }
}
