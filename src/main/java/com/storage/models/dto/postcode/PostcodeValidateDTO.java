package com.storage.models.dto.postcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
//@NoArgsConstructor
@AllArgsConstructor
public class PostcodeValidateDTO {
    private final int status;
    private final Boolean result;
    private final String error;
}

