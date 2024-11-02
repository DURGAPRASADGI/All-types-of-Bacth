package com.example.Batch.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "entityManagerFactoryBean",
		 transactionManagerRef = "platformTransactionManager",
			//basePackageClasses = PostgreRepository.class
			basePackages = "com.example.Batch.postgrerepo"

			
		)


public class PostgreSqlConfig {
	
  
	@Bean("entityManagerFactoryBean")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder,@Qualifier("postgreuniversitydatasource") DataSource dataSource) {
//		 Map<String, Object> properties = new HashMap<>();

		return builder
				.dataSource(dataSource)
				.packages("com.example.Batch.entity")
				 .persistenceUnit("university")
				.build();
		
	}
	
	@Bean
	public PlatformTransactionManager platformTransactionManager(@Qualifier("entityManagerFactoryBean") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
		
	}
	
	

}
