package com.storage.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String city;
    private String street;
    private String postCode;

    @OneToMany(
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(name = "warehouse_id")
    @JsonIgnore
    private List<StorageRoom> storageRooms = new ArrayList<>();


}
