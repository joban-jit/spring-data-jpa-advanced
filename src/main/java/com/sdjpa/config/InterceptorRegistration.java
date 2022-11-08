package com.sdjpa.config;

import com.sdjpa.interceptors.EncryptionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

//@Configuration commented out to disable interceptors
public class InterceptorRegistration implements HibernatePropertiesCustomizer {

//    @Autowired commented out to disable interceptors
    EncryptionInterceptor encryptionInterceptor;

//    commented out to disable interceptors
    @Override
    public void customize(Map<String, Object> hibernateProperties) {
//        hibernateProperties.put("hibernate.session_factory.interceptor", encryptionInterceptor);
    }
}
