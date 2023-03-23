package com.impllife.bankmock.services.jpa.converters;

import com.impllife.bankmock.data.entity.security.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role[], String> {
    @Override
    public String convertToDatabaseColumn(Role[] roles) {
        return null;
    }

    @Override
    public Role[] convertToEntityAttribute(String s) {
        return new Role[0];
    }
}
