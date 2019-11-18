package com.cristovantamayo.ubsvoce.controllers;

/**
 * Controller exclusivo para a sessao de upload
 * Fornece os entrypoint para configuracao inicial o aplicativo via http 
 * @author Cristovan
 */
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
 * Forne acesso ao formulario de upload do CSV fonte de dados da aplicacao
 * @author Cristovan
 *
 */
@Controller
@RequestMapping(value="/config")
public class FileUploadController {

	private final StorageService storageService;
	
	/**
	 * Injecao da dependencia de Servico do Storage (Autowired)
	 * @param storageService componente de servico do Storage
	 */
	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	/**
	 * Interface UI com formulario de importacao do arquivo CSV
	 * 
	 * @param model componente model string framework
	 * @return html pagina dinamica /import
	 * @throws IOException excecao do Storage
	 */
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
	
	/**
	 * Referencia html dos arquivos carregados
	 *  
	 * @param filename path do arquivo
	 * @return html porcao html com listagemde arquivos carregados
	 */
	@GetMapping("/import/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		// Dispondo o arquivo carregado para download anexando ao body sua referência
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
	
	/**
	 * End Point de Upload de arquivos
	 * 
	 * @param file arquivo enviado
	 * @param redirectAttributes componente para atributos do redirecionamento
	 * @return redirect redirecionamento pra o fomulario envio.
	 */
	@PostMapping("/import/uploader")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
		// Armazena
        storageService.store(file);
        
        // Prepara mensagem de Sucesso!
        redirectAttributes.addFlashAttribute("message",
                "Você obteve sucesso ao enviar o arquivo " + file.getOriginalFilename() + "!");
        
        // retorna ao formulário
        return "redirect:/config/import";
    }
	
	/**
	 * Tratamento de excecao referente ao Storage
	 * @param exc excecao do Storage
	 * @return entidade de resposta 404
	 */
	@ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
