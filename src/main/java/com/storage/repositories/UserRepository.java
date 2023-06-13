package com.storage.repositories;

import com.storage.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends UuidRepository<User, Long> {
    Boolean existsByEmail(String email);

    Optional<User> findByEmailIgnoreCase(String email);

    @Query("SELECT uuid FROM User WHERE LOWER(email)=LOWER(?1)")
    Optional<String> getUuidByEmail(String email);

    Optional<Long> findIdByEmail(String email);

    @Modifying
    @Query(value = "UPDATE User u SET u.enabled=TRUE WHERE u.id=:userId")
    void activateAccount(Long userId);

}
