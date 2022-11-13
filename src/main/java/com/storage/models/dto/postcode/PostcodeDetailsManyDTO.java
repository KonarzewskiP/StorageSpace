package com.storage.models.dto.postcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostcodeDetailsManyDTO {

    private int status;
    private List<PostcodeResultDTO> postcodeResultDTO;
}
