package com.sdjpa.config;

import com.sdjpa.domain.creditcard.CreditCard;
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
        basePackages = "com.sdjpa.repositories.creditcard",
        entityManagerFactoryRef = "cardEntityManagerFactory",
        transactionManagerRef = "cardTransactionManager"
)
@Configuration
public class CardDatabaseConfiguration {

    @Bean
    @ConfigurationProperties("spring.card.datasource")
    public DataSourceProperties cardDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.card.datasource.hikari")
    public DataSource cardDataSource(
            @Qualifier("cardDataSourceProperties") DataSourceProperties cardDataSourceProperties)
    {
        return cardDataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class) // to use database connections, we will use hikari
                .build();

    }

    @Bean
    public LocalContainerEntityManagerFactoryBean cardEntityManagerFactory(
            @Qualifier("cardDataSource") DataSource cardDataSource,
            EntityManagerFactoryBuilder builder
    ){
        Properties properties = new Properties();
        //spring.jpa.hibernate.ddl-auto
        properties.put("hibernate.hbm2ddl.auto", "validate");
        properties.put("hibernate.physical_naming_strategy",
                "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
        LocalContainerEntityManagerFactoryBean lcemfb = builder
                .dataSource(cardDataSource)
                .packages(CreditCard.class)
                .persistenceUnit("card")
                .build();
        lcemfb.setJpaProperties(properties);

        return lcemfb;

    }

    @Bean
    public PlatformTransactionManager cardTransactionManager(
            @Qualifier("cardEntityManagerFactory") LocalContainerEntityManagerFactoryBean cardEntityManagerFactory
    ){
        return new JpaTransactionManager(cardEntityManagerFactory.getObject());
    }


    @Bean
    @ConfigurationProperties(value = "spring.card.liquibase")
    public LiquibaseProperties cardLiquibaseProperties(){
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase cardLiquibase(
            @Qualifier("cardDataSource") DataSource cardDataSource,
            @Qualifier("cardLiquibaseProperties") LiquibaseProperties cardLiquibaseProperties
    ){
        return getSpringLiquibase(cardDataSource, cardLiquibaseProperties);
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
