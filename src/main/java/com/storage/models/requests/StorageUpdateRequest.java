package com.storage.models.requests;

import com.storage.models.enums.StorageRoomStatus;
import com.storage.models.enums.StorageSize;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorageUpdateRequest {
    @NotNull
    private StorageSize storageSize;
    @NotNull
    private StorageRoomStatus status;
}
