package com.cristovantamayo.ubsvoce.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cristovantamayo.ubsvoce.services.exceptions.StorageException;
import com.cristovantamayo.ubsvoce.services.exceptions.StorageFileNotFoundException;
import com.cristovantamayo.ubsvoce.services.storage.StorageProperties;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    
    @Autowired
    UnidadeService unidadeService;

    // Carrega e aplica as configurações do Storage
    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    // Tenta armazenar os arquivos 'upados' no diretorio configurado.
    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Falha ao armazenar o arquivo vazio " + file.getOriginalFilename());
            }
            
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
            
            // O arquivo CSV desejado
            File desiredFile = new File(load(file.getOriginalFilename()).toString());
            
            unidadeService.addCSVFile(desiredFile);
            
        } catch (IOException e) {
            throw new StorageException("Falha ao armazenar o arquivo " + file.getOriginalFilename(), e);
        }
    }

    // Retorna dos os arquivos encontrados no path root. No caso atual, somente 1.
    @Override
    public Stream<Path> loadAll() {
        try {
        	// Percore o Root do Stage, filtra e mapeia os items encontrados relativisando suas localizações
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Falha ao ler os arquivos armazenados", e);
        }

    }

    @Override
    public Path load(String filename) {
    	// Revolve o caminho realtivo do filename
        return rootLocation.resolve(filename);
    }
    
    // disponibiliza do arquivo como um recurso para requisiçoes e.g. downloads
    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Não foi possível ler o arquivo: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Não foi possível ler o arquivo: " + filename, e);
        }
    }

    // Apaga todos os arquivos carregados, no caso atual: 1
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    // Inicializa o diretorio Root do Storage
    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Não foi possível iniciar o storage", e);
        }
    }
}