package com.sdjpa.config;

import com.sdjpa.listeners.PostLoadListener;
import com.sdjpa.listeners.PreInsertListener;
import com.sdjpa.listeners.PreUpdateListener;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;


//@Component disable hibernate listeners
public class ListenerRegistration implements BeanPostProcessor {

    private final PostLoadListener postLoadListener;
    private final PreUpdateListener preUpdateListener;
    private final PreInsertListener preInsertListener;

    public ListenerRegistration(PostLoadListener postLoadListener, PreUpdateListener preUpdateListener, PreInsertListener preInsertListener) {
        this.postLoadListener = postLoadListener;
        this.preUpdateListener = preUpdateListener;
        this.preInsertListener = preInsertListener;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//disable hibernate listeners
//        if (bean instanceof LocalContainerEntityManagerFactoryBean lcemf){
//            SessionFactoryImpl sessionFactory = (SessionFactoryImpl) lcemf.getNativeEntityManagerFactory();
//            EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
//            registry.appendListeners(EventType.POST_LOAD, postLoadListener);
//            registry.appendListeners(EventType.PRE_UPDATE, preUpdateListener);
//            registry.appendListeners(EventType.PRE_INSERT, preInsertListener);
//
//        }

        return bean;
    }
}
