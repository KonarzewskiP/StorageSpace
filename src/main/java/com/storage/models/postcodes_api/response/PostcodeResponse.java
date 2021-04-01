package com.storage.models.postcodes_api.response;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.storage.models.postcodes_api.deserializer.PostcodeResponseDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonDeserialize(using = PostcodeResponseDeserializer.class)
public class PostcodeResponse {

    private Integer status;
    private List<Result> result;

}
