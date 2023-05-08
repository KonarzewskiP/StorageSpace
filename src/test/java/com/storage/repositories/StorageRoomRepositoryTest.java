package com.storage.repositories;

import com.storage.models.StorageRoom;
import com.storage.models.enums.StorageRoomStatus;
import com.storage.models.enums.StorageSize;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest(properties = {
        "spring.jpa.properties.javax.persistence.validation.mode=none"
})
class StorageRoomRepositoryTest {
    private static final Long WAREHOUSE_ID = 1L;

    @Autowired
    private StorageRoomRepository underTest;

    @Test
    void itShouldDeleteRecordsWithMatchingUuids() {
        //Given
        String uuid1 = "AAA";
        String uuid2 = "BBB";
        String uuid3 = "CCC";

        StorageRoom storageRoom1 = StorageRoom.builder()
                .uuid(uuid1)
                .storageSize(StorageSize.TELEPHONE_BOX)
                .status(StorageRoomStatus.AVAILABLE)
                .warehouseId(WAREHOUSE_ID)
                .build();
        StorageRoom storageRoom2 = StorageRoom.builder()
                .uuid(uuid2)
                .storageSize(StorageSize.TELEPHONE_BOX)
                .status(StorageRoomStatus.AVAILABLE)
                .warehouseId(WAREHOUSE_ID)
                .build();
        StorageRoom storageRoom3 = StorageRoom.builder()
                .uuid(uuid3)
                .storageSize(StorageSize.TELEPHONE_BOX)
                .status(StorageRoomStatus.AVAILABLE)
                .warehouseId(WAREHOUSE_ID)
                .build();
        underTest.saveAll(List.of(storageRoom1, storageRoom2, storageRoom3));
        //When
        Integer numberOfDeletedRooms = underTest.deleteAllByUuids(List.of(uuid1,uuid2));
        //Then
        assertThat(numberOfDeletedRooms).isEqualTo(2L);
    }
}
























