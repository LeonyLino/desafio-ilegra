package br.com.ilegra.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ilegra.service.ArquivoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("arquivorest")
@RequiredArgsConstructor
public class ArquivoRestController {
	
	private final ArquivoService aService;
	

	@Bean	
	@PostMapping
	public void uploadArquivo() {
		aService.fileRead();
	}


}
