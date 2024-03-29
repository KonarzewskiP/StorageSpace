package com.storage.models.requests;

import com.storage.models.enums.StorageRoomStatus;
import com.storage.models.enums.StorageSize;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record StorageUpdateRequest(
        @NotNull
        StorageSize storageSize,
        @NotNull
        StorageRoomStatus status
) {
}
