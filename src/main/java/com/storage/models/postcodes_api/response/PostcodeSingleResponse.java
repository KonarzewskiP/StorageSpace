package com.storage.models.postcodes_api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * This is a model class to hold location information.
 *
 * @author Pawel Konarzewski
 * @since 05/03/2021
 *
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostcodeSingleResponse {

    private Integer status;
    private String postcode;
    private double longitude;
    private double latitude;

    private String error;
}
