package com.storage.validator.base;

import java.util.Map;

public interface Validator<T> {
    Map<String, String> validate(T item);
}
