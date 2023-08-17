package com.utsa.FbGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class FbGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(FbGatewayApplication.class, args);
	}

}
