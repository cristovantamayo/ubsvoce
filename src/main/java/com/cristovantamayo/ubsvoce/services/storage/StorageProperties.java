package com.cristovantamayo.ubsvoce.services.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Seta, Armazena e recupera a localização do Storage
 * @author Cristovan
 */

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Pasta para armazenar arquivos
     */
    private String location = "upload-dir";
    
    /**
     * Recupera o caminho do diretório root do Storage
     * @return String location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Registra o diretório root do Storage
     * @param String location
     */
    public void setLocation(String location) {
        this.location = location;
    }

}