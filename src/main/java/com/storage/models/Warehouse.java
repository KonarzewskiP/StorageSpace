package com.storage.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.storage.models.enums.SpecType;
import lombok.*;
import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "warehouses")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

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
