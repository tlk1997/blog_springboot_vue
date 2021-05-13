package com.tlk.blog;


import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@EnableSwagger2Doc
@SpringBootApplication
public class QuestionApplication {
    public static void main(String[] args){
        SpringApplication.run(QuestionApplication.class, args);
    }
}
