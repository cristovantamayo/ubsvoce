package com.cristovantamayo.ubsvoce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller do Front-end da aplicacao
 * @author Cristovan
 */
@Controller
public class UbsvoceController {
	/**
	 * Recipiente de configuracoes do sistema
	 */
	@Autowired
	private Environment env;
	/**
	 * Endpoint principal (Home)
	 * 
	 * @param model parametro reenchido pelo spring framework
	 * @return html pagina html
	 */
	@GetMapping("/")
	public String frontpage(Model model){
		model.addAttribute("apiKey", env.getProperty("geocoding.apiKey"));
		return "Home";
	}
	
}
