package com.cristovantamayo.ubsvoce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UbsvoceController {
	
	@Autowired
	private Environment env;

	@GetMapping("/")
	public String add_source(Model model){
		model.addAttribute("apiKey", env.getProperty("geocoding.apiKey"));
		return "Home";
	}
	
}
