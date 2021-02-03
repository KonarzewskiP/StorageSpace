package com.storage.service;

import com.storage.repository.StorageRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StorageRoomService {

    private final StorageRoomRepository storageRoomRepository;



}
