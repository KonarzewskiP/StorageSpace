package com.storage.models;

import com.storage.models.base.AbstractObject;
import com.storage.models.enums.StorageDuration;
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
    @Column(name = "actual_price", nullable = false)
    private BigDecimal actualPrice;
    @Column(name = "discount")
    private BigDecimal discount;
    @Column(name = "discount_id")
    private Long discountId;
    @Enumerated(EnumType.STRING)
    @Column(name = "duration", nullable = false)
    private StorageDuration duration;
}
