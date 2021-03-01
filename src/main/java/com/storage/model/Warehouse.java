package com.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.storage.model.enums.Size;
import lombok.*;
import javax.persistence.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "warehouses")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
