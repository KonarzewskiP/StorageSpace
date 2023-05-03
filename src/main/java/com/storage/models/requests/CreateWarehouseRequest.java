package com.storage.models.requests;

import java.math.BigDecimal;

public record CreateWarehouseRequest(
        String name,
        String city,
        String postcode,
        String street,
        BigDecimal totalRentalAreaInM2
) {
}
