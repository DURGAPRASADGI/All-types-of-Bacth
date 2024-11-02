package com.example.Batch.config;



import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MultipleDataSources {

    @Primary
    @Bean(name = "source1")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource source1() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "source2")
    @ConfigurationProperties(prefix = "spring.people.datasource")
    public DataSource source2() {
    	 return DataSourceBuilder.create().build();
    }

    @Bean(name = "postgreuniversitydatasource")
    @ConfigurationProperties(prefix = "spring.university.datasource")
    public DataSource postgreuniversitydatasource() {
        return DataSourceBuilder.create().build();
    }
}