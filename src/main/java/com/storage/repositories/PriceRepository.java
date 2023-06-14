package com.storage.repositories;

import com.storage.models.Price;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PriceRepository extends UuidRepository<Price, Long> {

    Optional<Price> getByWarehouseId(Long warehouseId);

    @Modifying
    @Query("DELETE FROM Price WHERE uuid=:uuid")
    int deleteByUuid(String uuid);
}
