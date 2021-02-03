package com.storage.model.dto;

import com.storage.model.Gender;
import com.storage.model.Warehouse;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectorDto {

        private Long id;
        private String firstName;
        private String lastName;
        private Gender gender;
        private Boolean isValid;
        private Set<Warehouse> warehouses;

}
