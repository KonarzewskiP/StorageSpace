package com.storage.models.dto.postcode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class PostcodeDetails {

    private String postcode;
    @JsonProperty("latitude")
    private float lat;
    @JsonProperty("longitude")
    private float lng;
}
