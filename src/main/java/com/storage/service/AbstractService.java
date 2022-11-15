package com.storage.service;

import com.storage.exceptions.BadRequestException;
import com.storage.exceptions.NotFoundException;
import com.storage.models.base.AbstractObject;
import com.storage.repositories.UuidRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public abstract class AbstractService<T extends AbstractObject> {

    private final Class<T> clazz;
    private final UuidRepository<T, Long> repository;

    public T findByUuidForUpdate(String uuid) {
        if (StringUtils.isBlank(uuid))
            throw new BadRequestException("UUID can't be null or empty.");

        return repository.findByUuidForUpdate(uuid)
                .orElseThrow(() -> new NotFoundException(clazz.getSimpleName() + " not found with uuid: " + uuid));
    }

    public T findByUuid(String uuid) {
        if (StringUtils.isBlank(uuid))
            throw new BadRequestException("UUID can't be null or empty.");

        return repository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException(clazz.getSimpleName() + " not found with uuid: " + uuid));
    }

    public T findById(Long id) {
        if (id == null || id <= 0)
            throw new BadRequestException("Id can't be null or negative.");

        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(clazz.getSimpleName() + " not found with id: " + id));
    }
}
