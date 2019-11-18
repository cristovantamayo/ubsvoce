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
    private String location;
    
    /**
     * Recupera o caminho do diretorio root do Storage
     * @return location path raiz do Storage
     */
    public String getLocation() {
        return location;
    }

    /**
     * Registra o diretorio root do Storage
     * @param location path raiz do Storage
     */
    public void setLocation(String location) {
        this.location = location;
    }

}