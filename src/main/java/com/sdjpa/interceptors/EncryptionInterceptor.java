package com.sdjpa.interceptors;

import com.sdjpa.services.EncryptionService;
import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.Transaction;
import org.hibernate.metamodel.RepresentationMode;
import org.hibernate.metamodel.spi.EntityRepresentationStrategy;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;


@Component
public class EncryptionInterceptor implements Interceptor, Serializable
{
    @Autowired
    private EncryptionService encryptionService;

    @Override
    public boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        System.out.println("I'm in onSave");
        Object[] newState = processFields(entity, state, propertyNames, "onSave");
        return Interceptor.super.onSave(entity, id, newState, propertyNames, types);
    }


    @Override
    public boolean onLoad(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        System.out.println("I'm in onLoad");
        if(state==null){
            System.out.println("state is null");
            Arrays.stream(types).forEach(t->{
                System.out.println(t.getName());
            });
        }
//        Object[] newState = processFields(entity, state, propertyNames, "onLoad");
        return Interceptor.super.onLoad(entity, id, state, propertyNames, types);
    }


    @Override
    public boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException {
        System.out.println("I'm in onFlushDirty");
        Object[] newState = processFields(entity, currentState, propertyNames, "onFlushDirty");
        return Interceptor.super.onFlushDirty(entity, id, newState, previousState, propertyNames, types);
    }

    private Object[] processFields(Object object, Object[] state, String[] propertyNames, String type){
        List<String> annotationFields = getAnnotationFields(object);
        for(String field: annotationFields){
            for(int i=0; i<propertyNames.length; i++){
                if(propertyNames[i].equals(field)){
                    if(StringUtils.hasText(state[i].toString())){
                        if(("onSave".equals(type) || "onFlushDirty".equals(type))){
                            System.out.println("IN ONSAVE and ONFLUSHDIRTY");
                             state[i] = encryptionService.encrypt(state[i].toString());
                        }else if ("onLoad".equals(type)){
                            System.out.println("IN ONLOAD");
                            state[i] = encryptionService.decrypt(state[i].toString());
                        }
                    }
                }
            }
        };
        return state;
    }


    private List<String> getAnnotationFields(Object object){
        checkIfEncryptedAnnotatedObject(object);
        List<String> annotatedFields = new ArrayList<>();
        Class<?> clazz = object.getClass();
        for (Field field: clazz.getDeclaredFields()) {
            if(field.isAnnotationPresent(EncryptedString.class)){
                annotatedFields.add(field.getName());
            }
            // or we can do below
//            if (Objects.nonNull(field.getAnnotation(EncryptedString.class))){
//                annotatedFields.add(field.getName());
//            }
        }
        return annotatedFields;

    }

    private void checkIfEncryptedAnnotatedObject(Object object){
        if(Objects.isNull(object)){
            throw new NullPointerException("The object to encrypt is null");
        }
    }
}
