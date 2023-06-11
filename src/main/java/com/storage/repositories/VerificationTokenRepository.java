package com.storage.repositories;

import com.storage.models.VerificationToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    @Query("SELECT userId FROM VerificationToken WHERE token=:token")
    Optional<Long> findUserIdByToken(String token);

}
