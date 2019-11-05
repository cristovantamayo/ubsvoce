package com.cristovantamayo.ubsvoce.services;

/**
 * Interface para Armazemanento de Arquivo no processo de Upload
 * Uma boa compreensão sobre a Utilização da Interface pode ser obtida através dos links:
 * https://spring.io/guides/gs/uploading-files/
 * https://github.com/spring-guides/gs-uploading-files
 */

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void init();

    void store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
}
