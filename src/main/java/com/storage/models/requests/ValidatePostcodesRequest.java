package com.storage.models.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ValidatePostcodesRequest{
    private List<String> postcodes;
}

