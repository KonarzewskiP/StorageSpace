package com.storage.repositories;

import com.storage.models.User;

import java.util.Optional;

public interface UserRepository extends UuidRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
