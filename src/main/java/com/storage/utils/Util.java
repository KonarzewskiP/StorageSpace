package com.storage.utils;

import com.storage.model.StorageRoom;
import com.storage.model.enums.Size;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface Util {

    static List<StorageRoom> createStorageRoomsList() {
        return Arrays.stream(Size.values())
                .map(size -> StorageRoom.builder()
                        .size(size)
                        .build())
                .collect(Collectors.toList());
    }

}
