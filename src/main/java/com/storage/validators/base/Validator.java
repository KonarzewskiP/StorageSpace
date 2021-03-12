package com.storage.validators.base;

import java.util.Map;

public interface Validator<T> {
    Map<String, String> validate(T item);
}
