package com.storage.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.storage.models.base.AbstractObject;
import com.storage.models.enums.StorageSize;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "storage_rooms")
public class StorageRoom extends AbstractObject {

    @Enumerated(EnumType.STRING)
    private StorageSize storageSize;

    //TODO improve reservation systems
    private boolean reserved;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Warehouse warehouse;
}
