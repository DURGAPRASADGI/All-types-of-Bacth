package com.example.Batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
//@EnableScheduling
public class BatchApplication {

	public static void main(String[] args) {
//		System.exit(SpringApplication.exit(SpringApplication.run(BatchApplication.class, args)));
		SpringApplication.run(BatchApplication.class, args);
		
	}

}
