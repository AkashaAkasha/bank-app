package com.example.bank_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class BankAppApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("com.example");

    }

}
