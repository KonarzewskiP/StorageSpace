package com.storage.models.requests;

import com.storage.models.enums.Gender;
import com.storage.models.enums.Role;
import lombok.Builder;


@Builder
public record CreateUserRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        Role role,
        Gender gender
) {
}
