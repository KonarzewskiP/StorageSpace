package com.storage.model.dto;

import com.storage.model.enums.Gender;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectorDto {

        private Long id;
        private String firstName;
        private String lastName;
        private Gender gender;
}
