package com.storage.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String city;
    private String postcode;
    private String street;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "warehouse_id",unique = true)
    private Warehouse warehouse;

}
