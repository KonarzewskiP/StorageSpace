package com.storage.models.dto;

import com.storage.models.enums.TimeUnit;

import java.math.BigDecimal;


public record PriceDTO(
        String uuid,
        String warehouseUuid,
        TimeUnit timeUnit,
        BigDecimal telephoneBoxBasePrice,
        BigDecimal largeGardenShed,
        BigDecimal oneAndHalfGarages,
        BigDecimal threeSingleGarages,
        BigDecimal upTo2WeeksMultiplier,
        BigDecimal upTo4WeeksMultiplier,
        BigDecimal upTo8WeeksMultiplier,
        BigDecimal upTo6MonthsMultiplier,
        BigDecimal upTo1YearMultiplier,
        BigDecimal plus1YearMultiplier) {
}
