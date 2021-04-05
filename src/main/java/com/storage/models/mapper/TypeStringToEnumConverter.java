package com.storage.models.mapper;

import com.storage.exceptions.EnumParsingException;
import com.storage.models.enums.SpecType;
import org.springframework.core.convert.converter.Converter;

public class TypeStringToEnumConverter implements Converter<String, SpecType> {

    @Override
    public SpecType convert(String value) {
        try {
            return SpecType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new EnumParsingException(e.getMessage());
        }
    }
}
