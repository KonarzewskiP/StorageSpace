package com.storage.model.dto;

import com.storage.model.enums.Gender;
import com.storage.model.enums.Role;
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
