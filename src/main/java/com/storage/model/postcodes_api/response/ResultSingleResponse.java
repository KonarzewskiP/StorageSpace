package com.storage.model.postcodes_api.response;

import lombok.*;

/**
 * This is a model class to hold precise location information.
 *
 * @author Pawel Konarzewski
 * @since 05/03/2021
 *
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultSingleResponse {

    private String postcode;
    private Double longitude;
    private Double latitude;
}
