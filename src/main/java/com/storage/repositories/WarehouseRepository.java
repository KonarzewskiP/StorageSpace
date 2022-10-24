package com.storage.repositories;

import com.storage.models.Warehouse;
import com.storage.models.enums.SpecType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    List<Warehouse> findBySpecType(SpecType specType);

}
