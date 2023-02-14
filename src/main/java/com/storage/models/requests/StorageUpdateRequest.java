package com.storage.models.requests;

import com.storage.models.enums.StorageRoomStatus;
import com.storage.models.enums.StorageSize;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class StorageUpdateRequest {
    @NotNull
    private StorageSize storageSize;
    @NotNull
    private StorageRoomStatus status;
}
