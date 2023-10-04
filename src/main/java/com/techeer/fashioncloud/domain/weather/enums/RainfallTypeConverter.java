package com.techeer.fashioncloud.domain.weather.enums;

import jakarta.persistence.AttributeConverter;

// Enum DB 저장 형태 변환
//@Converter(autoApply = true)
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