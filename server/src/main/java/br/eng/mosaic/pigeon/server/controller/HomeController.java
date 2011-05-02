package br.eng.mosaic.pigeon.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/**/main.do")
	public void home() {
		
		/*
		 * checar usuario autenticado, caso nao redirecionar dar signUp
		 * checar mecanismos de segurança : considerar spring-security
		 */
		
	}

}