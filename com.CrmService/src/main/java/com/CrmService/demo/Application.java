package com.CrmService.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories("com.CrmService.repository")
@EntityScan("com.CrmService.model")
@ComponentScan( "com.CrmService.*")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
