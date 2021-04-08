package com.storage.models.postcodes_api.response;

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
