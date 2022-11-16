package com.storage.models.requests;

import com.storage.models.enums.StorageRoomStatus;
import com.storage.models.enums.StorageSize;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class StorageUpdateRequest {
    private StorageSize storageSize;
    private StorageRoomStatus status;
}
