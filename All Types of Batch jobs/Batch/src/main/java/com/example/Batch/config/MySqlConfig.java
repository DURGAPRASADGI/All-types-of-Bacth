package com.example.Batch.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.example.Batch.mysqlrepository",
    entityManagerFactoryRef = "mysqlEntityManagerFactoryBean", // Updated name
    transactionManagerRef = "mysqlTransactionManager" // Updated name
)
public class MySqlConfig {

	@Primary
    @Bean("mysqlEntityManagerFactoryBean") // Updated name
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
        @Qualifier("source2") DataSource dataSource,
        EntityManagerFactoryBuilder builder) {

        return builder
            .dataSource(dataSource)
            .packages("com.example.Batch.entity")
            .persistenceUnit("people")
            .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager mysqlTransactionManager(
        @Qualifier("mysqlEntityManagerFactoryBean") EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}
