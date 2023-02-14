package com.storage.service;

import com.storage.repositories.StorageRoomRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class StorageRoomServiceTest {

    @InjectMocks
    private StorageRoomService service;

    @Mock
    private StorageRoomRepository storageRoomRepository;
}

















