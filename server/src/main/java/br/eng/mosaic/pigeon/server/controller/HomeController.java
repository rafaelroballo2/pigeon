package br.eng.mosaic.pigeon.server.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.eng.mosaic.pigeon.common.domain.User;
import br.eng.mosaic.pigeon.server.controller.FacebookController.alias;
import br.eng.mosaic.pigeon.server.controller.FacebookController.uri;
import br.eng.mosaic.pigeon.server.helper.MimeType;

@Controller
public class HomeController {
	
	@RequestMapping( uri.main )
	public void home(HttpSession session, HttpServletResponse response) throws IOException {
		
		/*
		 * checar usuario autenticado, caso nao redirecionar dar signUp
		 * checar mecanismos de seguran�a : considerar spring-security
		 */
		
		// TODO refact to set request scope
		User user = (User) session.getAttribute( alias.user );
		String message = "welcome <" + user.name + "> " + user.email;
		display(response, message);
	}
	
	private void display(HttpServletResponse response, String content) throws IOException {
		response.setContentType(MimeType.text_plain.type);
		PrintWriter out = response.getWriter();
        out.write(content);
	}
	
}