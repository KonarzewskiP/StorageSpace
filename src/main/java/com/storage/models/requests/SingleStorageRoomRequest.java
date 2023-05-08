package com.storage.models.requests;

import com.storage.models.enums.StorageRoomStatus;
import com.storage.models.enums.StorageSize;
import lombok.Builder;

@Builder
public record SingleStorageRoomRequest(
        StorageSize storageSize,
        StorageRoomStatus status
) {
}
