package com.storage.model.postcodes_api;

import lombok.*;

import java.util.List;

/**
 * This is a model class to hold precise location information.
 *
 * @author Pawel Konarzewski
 * @since 05/03/2021
 *
 */


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultSingleResponse {

    private String postcode;
    private double longitude;
    private double latitude;

    public ResultSingleResponse(String postcode) {
        this.postcode = postcode;
    }

}
