package com.storage.models.dto.externals.postcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {

    private String postcode;
    private double longitude;
    private double latitude;
}
