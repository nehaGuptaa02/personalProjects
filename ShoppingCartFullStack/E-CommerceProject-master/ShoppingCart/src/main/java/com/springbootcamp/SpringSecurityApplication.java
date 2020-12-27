package com.springbootcamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication

@ComponentScan(basePackages = {"com.springbootcamp"})
@EnableJpaRepositories("com.springbootcamp.repos")
@RestController
@EntityScan(basePackageClasses =
        {
                SpringSecurityApplication.class,
                Jsr310JpaConverters.class
        })
@EnableAsync
public class SpringSecurityApplication {
    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);

    }

}
