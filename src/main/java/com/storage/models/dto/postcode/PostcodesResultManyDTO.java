package com.storage.models.dto.postcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostcodesResultManyDTO {
    private String query;
    private List<PostcodeResultDTO> result;
}
