package com.impllife.bankmock.services.jpa.converters;

import javax.persistence.AttributeConverter;

public class EnumConverter<E, ID> implements AttributeConverter<WithId<ID>, ID> {
    private final WithId<ID>[] values;

    public EnumConverter(WithId<ID>[] values) {
        this.values = values;
    }

    @Override
    public ID convertToDatabaseColumn(WithId<ID> tWithId) {
        if (tWithId == null) {
            return null;
        }
        return tWithId.getId();
    }

    @Override
    public WithId<ID> convertToEntityAttribute(ID t) {
        if (t == null) {
            return null;
        }
        for (WithId<ID> value : values) {
            if (t.equals(value.getId())) return value;
        }
        throw new IllegalArgumentException("Not exist element with id=" + t);
    }
}
