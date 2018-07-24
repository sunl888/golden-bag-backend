package com.zmdev.goldenbag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.zmdev.goldenbag", "com.zmdev.fatesdk"})
public class GoldenBagApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoldenBagApplication.class, args);
    }
}
