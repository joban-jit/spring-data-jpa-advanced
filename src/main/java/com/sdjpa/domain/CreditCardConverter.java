package com.sdjpa.domain;

import com.sdjpa.config.SpringContextHelper;
import com.sdjpa.services.EncryptionService;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CreditCardConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return getEncryptedService().encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return getEncryptedService().decrypt(dbData);
    }

    private EncryptionService getEncryptedService(){
        return SpringContextHelper.getApplicationContext().getBean(EncryptionService.class);
    }

}
