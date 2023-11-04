package com.ozapp.foreignexchanger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ozapp.foreignexchanger")
public class ForeignExchangerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ForeignExchangerApplication.class, args);
    }

}
