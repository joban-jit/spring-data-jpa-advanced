package com.sdjpa.config;

import com.sdjpa.domain.creditcard.CreditCard;
import com.sdjpa.domain.creditcardholder.CreditCardHolder;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;


import javax.sql.DataSource;
import java.util.Properties;

@EnableJpaRepositories(
        basePackages = "com.sdjpa.repositories.creditcardholder",
        entityManagerFactoryRef = "cardHolderEntityManagerFactory",
        transactionManagerRef = "cardHolderTransactionManager"
)
@Configuration
public class CardHolderDatabaseConfiguration {

    @Bean
    @ConfigurationProperties("spring.cardholder.datasource")
    public DataSourceProperties cardHolderDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.cardholder.datasource.hikari")
    public DataSource cardHolderDataSource(
            @Qualifier("cardHolderDataSourceProperties") DataSourceProperties cardHolderDataSourceProperties
    )
    {
        return cardHolderDataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean cardHolderEntityManagerFactory(
            @Qualifier("cardHolderDataSource") DataSource cardHolderDataSource,
            EntityManagerFactoryBuilder builder
    ){
        Properties properties = new Properties();
        //spring.jpa.hibernate.ddl-auto
        properties.put("hibernate.hbm2ddl.auto", "validate");
        properties.put("hibernate.physical_naming_strategy",
                "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
        LocalContainerEntityManagerFactoryBean lcemfb = builder
                .dataSource(cardHolderDataSource)
                .packages(CreditCardHolder.class)
                .persistenceUnit("cardholder")
                .build();
        lcemfb.setJpaProperties(properties);

        return lcemfb;
    }

    @Bean
    public PlatformTransactionManager cardHolderTransactionManager(
            @Qualifier("cardHolderEntityManagerFactory") LocalContainerEntityManagerFactoryBean cardHolderEntityManagerFactory
    ){
       return new JpaTransactionManager(cardHolderEntityManagerFactory.getObject());
    }


    @Bean
    @ConfigurationProperties(value = "spring.cardholder.liquibase")
    public LiquibaseProperties cardholderLiquibaseProperties(){
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase cardholderLiquibase(
            @Qualifier("cardHolderDataSource") DataSource cardHolderDataSource,
            @Qualifier("cardholderLiquibaseProperties") LiquibaseProperties cardholderLiquibaseProperties
    ){
        return getSpringLiquibase(cardHolderDataSource, cardholderLiquibaseProperties);
    }

    private SpringLiquibase getSpringLiquibase(DataSource dataSource, LiquibaseProperties liquibaseProperties) {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setChangeLog(liquibaseProperties.getChangeLog());
        springLiquibase.setDataSource(dataSource);
        springLiquibase.setContexts(liquibaseProperties.getContexts());
        springLiquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        springLiquibase.setShouldRun(liquibaseProperties.isEnabled());
        return springLiquibase;
    }

}
