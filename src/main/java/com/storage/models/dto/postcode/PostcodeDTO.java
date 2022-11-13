package com.storage.models.dto.postcode;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonDeserialize
public class PostcodeDTO {
    private int status;
    private PostcodeResultDTO postcodeResultDTO;
}
