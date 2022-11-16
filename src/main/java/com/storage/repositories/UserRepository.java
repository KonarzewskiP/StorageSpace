package com.storage.repositories;

import com.storage.models.User;

public interface UserRepository extends UuidRepository<User, Long> {
    Boolean existsByEmail(String email);
}
