package com.storage.repositories;

import com.storage.models.Price;

import java.util.Optional;

public interface PriceRepository extends UuidRepository<Price, Long> {

    Optional<Price> getByWarehouseId(Long warehouseId);
}
