package com.cristovantamayo.ubsvoce.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ApplicationAndConfiguration {


    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ApplicationAndConfiguration.class, new String[]{});
    }

}