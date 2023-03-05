package com.storage.models.dto;

import com.storage.models.enums.Gender;
import com.storage.models.enums.Role;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private Gender gender;
}
