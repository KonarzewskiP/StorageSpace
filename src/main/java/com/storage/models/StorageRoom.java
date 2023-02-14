package com.storage.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.storage.models.base.AbstractObject;
import com.storage.models.enums.StorageRoomStatus;
import com.storage.models.enums.StorageSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

//TODO improve identification of specific storage rooms.
// For example, allow adding unique codes for specific rooms to
// make them easier to serach in the Warehouse.
// What if there will be a need for a warehouse to resize and reorganize rooms.


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "storage_rooms")
public class StorageRoom extends AbstractObject {

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_size", nullable = false)
    private StorageSize storageSize;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StorageRoomStatus status;

    //TODO improve reservation systems
    private boolean reserved;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "warehouse_id", nullable = false)
    private Long warehouseId;
}
