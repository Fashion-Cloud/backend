package com.techeer.fashioncloud.domain.weather.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RainfallTypeConverter implements AttributeConverter<RainfallType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(RainfallType attribute) {
        return attribute.getCode();
    }

    @Override
    public RainfallType convertToEntityAttribute(Integer dbData) {
        return RainfallType.findOf(dbData);
    }
}