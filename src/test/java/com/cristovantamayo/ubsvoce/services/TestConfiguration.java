package com.cristovantamayo.ubsvoce.services;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan(basePackages={"com.cristovantamayo.ubsvoce.services.GeocodingService"})
@ImportResource("classpath:spring/spring-main.xml")
public class TestConfiguration {

}
