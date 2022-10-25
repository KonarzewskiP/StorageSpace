package com.storage.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.storage.models.base.AbstractObject;
import com.storage.models.enums.SpecType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "warehouses")
public class Warehouse extends AbstractObject {

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(name = "warehouse_id")
    @JsonIgnore
    @Builder.Default
    private List<StorageRoom> storageRooms = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private SpecType specType = SpecType.REGULAR;
}
