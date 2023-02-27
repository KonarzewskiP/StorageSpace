package com.storage.models;

import com.storage.models.enums.StorageSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "prices")
public class Price extends AbstractObject {
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    @Column(name = "storage_size", nullable = false)
    private StorageSize storageSize;
    @Column(name = "warehouse_id", nullable = false)
    protected Long warehouseId;
}
