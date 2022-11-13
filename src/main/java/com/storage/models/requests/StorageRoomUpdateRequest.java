package com.storage.models.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.storage.models.enums.StorageSize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class StorageRoomUpdateRequest {
    private StorageSize storageSize;
    private Boolean reserved;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

}
