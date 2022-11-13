package com.storage.models.dto.postcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostcodeResultDTO {
    private String postcode;
    private double longitude;
    private double latitude;
}
