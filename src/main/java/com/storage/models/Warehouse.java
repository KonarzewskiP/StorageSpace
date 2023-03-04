package com.storage.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "warehouses")
public class Warehouse extends AbstractObject {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "postcode", nullable = false)
    private String postcode;
    @Column(name = "street", nullable = false)
    private String street;
    @Column(name = "lat", nullable = false)
    private float lat;
    @Column(name = "lng", nullable = false)
    private float lng;
    @Column(name = "total_rental_area_in_m2", nullable = false)
    private BigDecimal totalRentalAreaInM2;
}
