package com.storage.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.storage.models.enums.StorageSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class StorageRoomDto {

    private Long id;
    private StorageSize storageSize;
    private Boolean reserved;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
