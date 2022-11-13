package com.storage.repositories;

import com.storage.models.Warehouse;
import com.storage.models.enums.SpecType;

import java.util.List;

public interface WarehouseRepository extends UuidRepository<Warehouse, Long> {
    List<Warehouse> findBySpecType(SpecType specType);
}
