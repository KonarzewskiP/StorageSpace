package com.storage.models.postcodes_api.response;

import lombok.Data;

@Data
public class PostcodeValidationResponse {

    private int status;
    private String result;

}
