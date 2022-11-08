package com.sdjpa.listeners;

import com.sdjpa.custom_annotations.EncryptedString;
import com.sdjpa.services.EncryptionService;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.stream.IntStream;

public abstract class AbstractEncryptionListener {

    private final EncryptionService encryptionService;

    public AbstractEncryptionListener(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    public void encrypt(Object[] state, String[] propertyNames, Object object){
        ReflectionUtils.doWithFields(object.getClass(), field-> encryptField(field, state, propertyNames), this::isFieldEncrypted);
    }

    public void decrypt(Object object){
        ReflectionUtils.doWithFields(object.getClass(), field -> decryptField(field, object), field-> this.isFieldEncrypted(field));
    }

    private void encryptField(Field field, Object[] state, String[] propertyNames){
        int propertyIndex = getPropertyIndex(field.getName(), propertyNames);
        Object currentValue = state[propertyIndex];
        state[propertyIndex] = encryptionService.encrypt(currentValue.toString());
    }

    private boolean isFieldEncrypted(Field field){
        return AnnotationUtils.findAnnotation(field, EncryptedString.class) != null;
    }

    private void decryptField(Field field, Object object){
        field.setAccessible(true);
        Object value = ReflectionUtils.getField(field, object);
        ReflectionUtils.setField(field, object, encryptionService.decrypt(value.toString()));
    }

    private int getPropertyIndex(String name, String[] properties){

        for(int i = 0; i<properties.length;i++){
            if(name.equals(properties[i])){
                return i;
            }
        }
        throw new IllegalArgumentException("Property not found: "+name);
    }



}
