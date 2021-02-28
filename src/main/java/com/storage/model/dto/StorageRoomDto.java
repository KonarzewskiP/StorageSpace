package com.storage.model.dto;

import com.storage.model.enums.Size;
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
    private Size size;
    private Boolean reserved;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
