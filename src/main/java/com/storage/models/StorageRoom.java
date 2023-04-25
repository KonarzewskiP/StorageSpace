package com.storage.models;

import com.storage.models.enums.StorageRoomStatus;
import com.storage.models.enums.StorageSize;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


//TODO improve identification of specific storage rooms.
// For example, allow adding unique codes for specific rooms to
// make them easier to serach in the Warehouse.
// What if there will be a need for a warehouse to resize and reorganize rooms.


@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "storage_rooms")
public class StorageRoom extends AbstractObject {

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_size", nullable = false)
    private StorageSize storageSize;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StorageRoomStatus status;

    //TODO improve reservation systems
//    private boolean reserved;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//    @Column(name = "start_date")
//    private LocalDate startDate;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//    @Column(name = "end_date")
//    private LocalDate endDate;

    @Column(name = "warehouse_id", nullable = false)
    private Long warehouseId;
}
