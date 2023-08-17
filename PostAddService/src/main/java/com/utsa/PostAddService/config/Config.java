package com.utsa.PostAddService.config;

import com.netflix.discovery.EurekaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration

public class Config {
    Logger logger = LoggerFactory.getLogger(Config.class);
    @Autowired
    EurekaClient eurekaClient;

    @Bean
    @DependsOn("restTemplateBuilder")
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.setConnectTimeout(Duration.ofMillis(2000)).build();
    }

    @Bean
    public RestTemplateBuilder restTemplateBuilder(){
        return new RestTemplateBuilder();
    }
}


