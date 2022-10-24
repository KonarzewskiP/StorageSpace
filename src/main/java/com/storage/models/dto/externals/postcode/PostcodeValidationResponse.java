package com.storage.models.dto.externals.postcode;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostcodeValidationResponse {
    private int status;
    private Boolean result;
}
