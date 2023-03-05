package com.storage.models.dto;

import com.storage.models.enums.StorageRoomStatus;
import com.storage.models.enums.StorageSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StorageRoomDto {
    private String uuid;
    private StorageSize storageSize;
    private StorageRoomStatus status;
}
