package com.storage.models.requests;

import com.storage.models.enums.Gender;
import com.storage.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private Gender gender;
}
