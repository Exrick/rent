package com.rent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.servlet.MultipartConfigElement;

/**
 * @author Exrickx
 */
@SpringBootApplication
//启用JPA审计
@EnableJpaAuditing
public class RentApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentApplication.class, args);
    }
}
