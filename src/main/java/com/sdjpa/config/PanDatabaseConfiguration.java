package com.sdjpa.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PanDatabaseConfiguration {

    @Bean
    @ConfigurationProperties("spring.pan.datasource")
    public DataSourceProperties panDataSourceProperties(){
        return new DataSourceProperties();
    }
}
