package com.storage.models.postcodes_api.response;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostcodeBulkResponse {

    private String status;
    @SerializedName("result")
    private List<ResultSingleResponse> result;

}




