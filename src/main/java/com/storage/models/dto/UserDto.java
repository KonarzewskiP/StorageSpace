package com.storage.models.dto;

import com.storage.models.enums.Gender;
import com.storage.models.enums.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private Role role;
        private Gender gender;
}
