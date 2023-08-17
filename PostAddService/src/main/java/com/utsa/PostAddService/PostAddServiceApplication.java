package com.utsa.PostAddService;

import com.utsa.PostAddService.config.RibbonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@RibbonClient(name = "feed-service", configuration = RibbonConfiguration.class)
@SpringBootApplication
public class PostAddServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostAddServiceApplication.class, args);
	}

}
