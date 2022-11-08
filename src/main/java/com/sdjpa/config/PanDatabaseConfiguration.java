package com.sdjpa.config;

import com.sdjpa.domain.pan.CreditCardPAN;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class PanDatabaseConfiguration {

    @Primary
    @Bean
    @ConfigurationProperties("spring.pan.datasource")
    public DataSourceProperties panDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource panDataSource(
            @Qualifier("panDataSourceProperties") DataSourceProperties panDataSourceProperties
    )
    {
        return panDataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean panEntityManagerFactory(
            @Qualifier("panDataSource") DataSource panDataSource,
            EntityManagerFactoryBuilder builder
    ){
        return builder
                .dataSource(panDataSource)
                .packages(CreditCardPAN.class)
                .persistenceUnit("pan")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager panTransactionManager(
            @Qualifier("panEntityManagerFactory") LocalContainerEntityManagerFactoryBean panEntityManagerFactory
    ){
        return new JpaTransactionManager(panEntityManagerFactory.getObject());
    }
}
