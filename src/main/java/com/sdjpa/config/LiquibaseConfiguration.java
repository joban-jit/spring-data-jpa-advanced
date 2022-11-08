package com.sdjpa.config;


import liquibase.Liquibase;
import liquibase.integration.spring.MultiTenantSpringLiquibase;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class LiquibaseConfiguration {

//    @Bean
//    @ConfigurationProperties(value = "spring.cardholder.liquibase")
    public LiquibaseProperties cardholderLiquibaseProperties(){
        return new LiquibaseProperties();
    }

//    @Bean
//    @ConfigurationProperties(value = "spring.card.liquibase")
    public LiquibaseProperties cardLiquibaseProperties(){
        return new LiquibaseProperties();
    }
//    @Bean
//    @ConfigurationProperties(value = "spring.pan.liquibase")
    public LiquibaseProperties panLiquibaseProperties(){
        return new LiquibaseProperties();
    }


}
