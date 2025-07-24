package com.sziit.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.sziit.job", "com.sziit.common"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.sziit.common.feign")
public class JobApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }
} 