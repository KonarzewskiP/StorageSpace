package com.storage.service;

import com.storage.exceptions.BadRequestException;
import com.storage.exceptions.NotFoundException;
import com.storage.models.base.AbstractObject;
import com.storage.repositories.UuidRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractService<T extends AbstractObject> {

    private final Class<T> clazz;
    private final UuidRepository<T, Long> repository;

    public T findByUuidForUpdate(String uuid) {
        if (uuid == null || uuid.length() == 0)
            throw new BadRequestException("UUID can't be null or empty.");

        return repository.findByUuidForUpdate(uuid)
                .orElseThrow(() -> new NotFoundException(clazz.getSimpleName() + " not found with uuid " + uuid));
    }

}
