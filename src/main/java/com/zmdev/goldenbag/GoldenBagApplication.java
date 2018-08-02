package com.zmdev.goldenbag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = {"com.zmdev.goldenbag", "com.zmdev.fatesdk"})
@EnableScheduling
public class GoldenBagApplication {

    public static void main(String[] args) {

        SpringApplication.run(GoldenBagApplication.class, args);
    }
}
