package com.storage.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "verification_tokens")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    protected Long id;
    @Column(unique = true)
    private String token;
    @Column(name = "expired_time", nullable = false)
    private LocalDateTime expiredTime; // TODO zrobic ZoneLocalDateTime
    @Column(name = "user_id")
    private Long userId;
}
