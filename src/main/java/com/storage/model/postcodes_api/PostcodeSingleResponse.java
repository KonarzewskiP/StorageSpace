package com.storage.model.postcodes_api;

import com.google.gson.annotations.SerializedName;
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

    private String status;
    @SerializedName("result")
    private ResultSingleResponse resultSingleResponse;
    private String error;
}
