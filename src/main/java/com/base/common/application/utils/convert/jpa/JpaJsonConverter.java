package com.base.common.application.utils.convert.jpa;

import java.io.IOException;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class JpaJsonConverter implements AttributeConverter<Object, String> {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        try {
            String __value = objectMapper.writeValueAsString(attribute);
            return __value;
        } catch (JsonProcessingException ex) {
            return null;
        }
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null)
                return null;
            Object __value = objectMapper.readValue(dbData, Object.class);
            return __value;
        } catch (IOException ex) {
            return null;
        }
    }

}
