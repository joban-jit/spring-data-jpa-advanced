package com.sdjpa.domain;

import com.sdjpa.config.SpringContextHelper;
import com.sdjpa.services.EncryptionService;
import jakarta.persistence.*;

public class CreditCardJPACallback {



    @PrePersist
    @PreUpdate
    public void beforeInsertOrUpdate( CreditCard creditCard){
        System.out.println("before update/insert was called ...");
        creditCard.setCreditCardNumber(getEncrpytionService().encrypt(creditCard.getCreditCardNumber()));

    }

    // here we want entity to be returned by update/insert also be unencrypted
    @PostPersist
    @PostLoad
    @PostUpdate
    public void postLoad(CreditCard creditCard){
        System.out.println("Post load was called ... ");
        creditCard.setCreditCardNumber(getEncrpytionService().decrypt(creditCard.getCreditCardNumber()));
    }

    private EncryptionService getEncrpytionService(){
        return SpringContextHelper.getApplicationContext().getBean(EncryptionService.class);
    }
}
