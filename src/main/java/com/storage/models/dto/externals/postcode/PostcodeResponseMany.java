package com.storage.models.dto.externals.postcode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostcodeResponseMany {

    private int status;
    private List<ResultMany> result;
}
