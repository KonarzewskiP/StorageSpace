package com.storage.model.postcodes_api;

import lombok.*;

import java.util.List;

/**
 * This is a model class to hold postcodes to call
 * third party API for bulk lookup of postcodes.
 *
 * @author Pawel Konarzewski
 * @since 06/03/2021
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostcodeBulkRequest {

    private List<String> postcodes;

}
