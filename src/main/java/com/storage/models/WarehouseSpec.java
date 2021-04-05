package com.storage.models;

import lombok.Value;

import java.util.Map;

@Value
public class WarehouseSpec {
    Map<String, Object> specification;
}
