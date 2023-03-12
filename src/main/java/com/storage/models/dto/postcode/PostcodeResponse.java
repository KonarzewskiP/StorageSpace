package com.storage.models.dto.postcode;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class PostcodeResponse {
    private Integer status;
    private String error;
}
