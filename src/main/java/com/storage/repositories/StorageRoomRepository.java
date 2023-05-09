package com.storage.repositories;

import com.storage.models.StorageRoom;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StorageRoomRepository extends UuidRepository<StorageRoom, Long> {

    int deleteAllByWarehouseId(Long warehouseId);

    @Modifying
    @Query("DELETE FROM StorageRoom WHERE uuid IN (:uuids)")
    int deleteAllByUuids(List<String> uuids);
}
