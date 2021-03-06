package com.storage.model.postcodes_api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultBulkResponse {

    private String query;
    private List<ResultSingleResponse> result;
}
