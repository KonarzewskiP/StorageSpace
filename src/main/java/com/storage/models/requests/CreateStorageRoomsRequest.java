package com.storage.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateStorageRoomsRequest(
        @NotBlank
        String warehouseUuid,
        @NotNull
        List<SingleStorageRoomRequest> storageRoomList
        ) {
}
