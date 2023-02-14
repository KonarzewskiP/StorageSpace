package com.storage.repositories;

import com.storage.models.base.AbstractObject;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@NoRepositoryBean
public interface UuidRepository<T extends AbstractObject, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    @QueryHints(value = {
            @QueryHint(name = org.hibernate.jpa.QueryHints.HINT_READONLY, value = "true"),
    })
    Optional<T> findByUuid(String uuid);

    @Query("SELECT t FROM #{#entityName} t WHERE t.uuid=:uuid")
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<T> findByUuidForUpdate(@Param("uuid") String uuid);

    @Query("SELECT t.id FROM #{#entityName} t WHERE t.uuid=:uuid")
    Optional<Long> findIdByUuid(@Param("uuid") String uuid);

    @Query("SELECT t.uuid FROM #{#entityName} t WHERE t.id=:id")
    String findUuidById(@Param("id") ID id);
}
