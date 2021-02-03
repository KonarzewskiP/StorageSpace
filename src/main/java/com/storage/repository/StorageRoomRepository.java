package com.storage.repository;

import com.storage.model.StorageRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRoomRepository extends JpaRepository<StorageRoom, Long> {
}
