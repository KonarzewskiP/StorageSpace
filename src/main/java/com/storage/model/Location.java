package com.storage.model;

import lombok.Builder;
import lombok.Data;

/**
 * This is a model class to hold location information.
 *
 * @author Pawel Konarzewski
 * @since 05/03/2021
 *
 */

@Data
@Builder
public class Location {

    private String status;
    private Result result;
    private String error;
}
