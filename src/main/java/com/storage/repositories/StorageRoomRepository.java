package com.storage.repositories;

import com.storage.models.StorageRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRoomRepository extends JpaRepository<StorageRoom, Long> {
}
