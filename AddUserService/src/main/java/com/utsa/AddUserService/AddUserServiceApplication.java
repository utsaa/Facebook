package com.utsa.AddUserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class AddUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddUserServiceApplication.class, args);
	}

}
