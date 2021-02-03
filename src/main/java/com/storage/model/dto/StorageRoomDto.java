package com.storage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StorageRoomDto {

    private Long id;
    private double sizeM2;
    private boolean reserved;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
