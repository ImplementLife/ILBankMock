package com.impllife.bankmock.data.convert;

import com.impllife.bankmock.data.entity.BillingStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BillingStatusConverter implements AttributeConverter<BillingStatus, Character> {
    private final BillingStatus[] values = BillingStatus.values();

    @Override
    public Character convertToDatabaseColumn(BillingStatus status) {
        if (status == null) {
            return null;
        }
        return status.getId();
    }

    @Override
    public BillingStatus convertToEntityAttribute(Character id) {
        if (id == null) {
            return null;
        }
        for (BillingStatus value : values) {
            if (value.getId() == id) return value;
        }
        throw new IllegalArgumentException("Not exist role with id=" + id);
    }
}
