package com.storage.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@ToString
@SuperBuilder
@MappedSuperclass
@Getter
public abstract class AbstractObject implements Serializable, Cloneable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    protected Long id;

    @Column(name = "uuid", updatable = false, nullable = false, unique = true)
    protected String uuid;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    protected LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    protected LocalDateTime updatedAt;


}
