package com.storage.models.dto.externals.postcode;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class PostcodeValidationResponse {

    private int status;
    private Boolean result;
}
