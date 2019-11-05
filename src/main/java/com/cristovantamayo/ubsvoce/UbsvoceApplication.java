package com.cristovantamayo.ubsvoce;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.cristovantamayo.ubsvoce.services.StorageService;
import com.cristovantamayo.ubsvoce.services.storage.StorageProperties;

@SpringBootApplication

// Configurando o Storage para receber o arquivo via upload
@EnableConfigurationProperties(StorageProperties.class)
public class UbsvoceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UbsvoceApplication.class, args);
	}
	
	// Reinicia o serviço do Storage
	// Ref: https://github.com/spring-guides/gs-uploading-files
	@Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
        	// Métodos fornecidos pela interface storageService
            //storageService.deleteAll();
            //storageService.init();
        };
    }

}
