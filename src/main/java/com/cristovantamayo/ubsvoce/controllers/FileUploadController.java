package com.cristovantamayo.ubsvoce.controllers;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cristovantamayo.ubsvoce.services.StorageService;
import com.cristovantamayo.ubsvoce.services.exceptions.StorageFileNotFoundException;

/**
 * Controller exclusivo para a sessão de upload
 * @author Cristovan
 */

@Controller
public class FileUploadController {

	private final StorageService storageService;
	
	// Injeta Serviço do Storage
	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	// Interface UI de importação de arquivo CSV
	@RequestMapping(value="/import", method=RequestMethod.GET)
	public String add_source(Model model) throws IOException {
		
		/* Remeter um array contendo os arquivos carregados, no caso: somente um.
		 * Chamando o método loadAll são retornados os arquivos carregados
		 * em seguida os mapeia para dispor as suas referências no array;*/
		model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(
                										FileUploadController.class,
                										"serveFile",
                										path.getFileName().toString())
                									.build().toString()
                ).collect(Collectors.toList()));
		
		return "insert";
	}
	
	// Prepara a referência dos arquivos carregados 
	@GetMapping("/import/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		// Dispondo o arquivo carregado para download anexando ao body sua referência
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
	
	// End Point de Carregamento
	@PostMapping("/import/uploader")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
		
		// Armazena
        storageService.store(file);
        
        // Prepara mensagem de Sucesso!
        redirectAttributes.addFlashAttribute("message",
                "Você obteve sucesso ao enviar o arquivo " + file.getOriginalFilename() + "!");
        
        // retorna ao formulário
        return "redirect:/import";
    }
	
	// Tratando exceções referente ao Storage
	@ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
