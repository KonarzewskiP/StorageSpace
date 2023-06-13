package com.storage.models;

import com.storage.models.enums.TimeUnit;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Builder
@Table(name = "prices")
public class Price extends AbstractObject {

    @Column(name = "warehouse_id", nullable = false)
    protected Long warehouseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "time_unit", nullable = false)
    private TimeUnit timeUnit;

    @Column(name = "telephone_box", nullable = false)
    private BigDecimal telephoneBoxBasePrice;
    @Column(name = "large_garden_shed", nullable = false)
    private BigDecimal largeGardenShed;
    @Column(name = "one_and_half_garages", nullable = false)
    private BigDecimal oneAndHalfGarages;
    @Column(name = "three_single_garages", nullable = false)
    private BigDecimal threeSingleGarages;

    @Column(name = "up_to_2_weeks_multiplier", nullable = false)
    private BigDecimal upTo2WeeksMultiplier;
    @Column(name = "up_to_4_weeks_multiplier", nullable = false)
    private BigDecimal upTo4WeeksMultiplier;
    @Column(name = "up_to_8_weeks_multiplier", nullable = false)
    private BigDecimal upTo8WeeksMultiplier;
    @Column(name = "up_to_6_months_multiplier", nullable = false)
    private BigDecimal upTo6MonthsMultiplier;
    @Column(name = "up_to_1_year_multiplier", nullable = false)
    private BigDecimal upTo1YearMultiplier;
    @Column(name = "plus_1_year_multiplier")
    private BigDecimal plus1YearMultiplier;
}
