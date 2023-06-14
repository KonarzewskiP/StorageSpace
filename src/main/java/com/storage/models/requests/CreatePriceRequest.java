package com.storage.models.requests;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CreatePriceRequest(
        @NotNull
        String warehouseUuid,
        @NotNull
        @PositiveOrZero
        BigDecimal telephoneBoxBasePrice,
        @NotNull
        @PositiveOrZero
        BigDecimal largeGardenShed,
        @NotNull
        @PositiveOrZero
        BigDecimal oneAndHalfGarages,
        @NotNull
        @PositiveOrZero
        BigDecimal threeSingleGarages,
        @NotNull
        @PositiveOrZero
        BigDecimal upTo2WeeksMultiplier,
        @NotNull
        @PositiveOrZero
        BigDecimal upTo4WeeksMultiplier,
        @NotNull
        @PositiveOrZero
        BigDecimal upTo8WeeksMultiplier,
        @NotNull
        @PositiveOrZero
        BigDecimal upTo6MonthsMultiplier,
        @NotNull
        @PositiveOrZero
        BigDecimal upTo1YearMultiplier,

        @PositiveOrZero
        BigDecimal plus1YearMultiplier) {
}
