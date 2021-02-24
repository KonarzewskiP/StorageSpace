package com.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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

    @ManyToOne()
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Director director;


    @OneToMany(
            cascade = {CascadeType.ALL}
    )
    @JsonIgnore
    private List<StorageRoom> storageRooms = new ArrayList<>();

}
