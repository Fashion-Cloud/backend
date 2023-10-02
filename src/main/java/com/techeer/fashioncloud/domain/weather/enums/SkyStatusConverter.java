package com.techeer.fashioncloud.domain.weather.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SkyStatusConverter implements AttributeConverter<SkyStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SkyStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public SkyStatus convertToEntityAttribute(Integer dbData) {
        return SkyStatus.findOf(dbData);
    }
}