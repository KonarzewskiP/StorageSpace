package com.storage.models.postcodes_api.response;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostcodeValidationResponse {

    private int status;
    private Boolean result;
}
