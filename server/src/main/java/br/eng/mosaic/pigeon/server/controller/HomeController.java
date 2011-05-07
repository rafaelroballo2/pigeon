package br.eng.mosaic.pigeon.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.eng.mosaic.pigeon.common.dto.UserSocialInfo;
import br.eng.mosaic.pigeon.server.controller.FacebookController.uri;
import br.eng.mosaic.pigeon.server.helper.MimeType;

@Controller
public class HomeController {
	
	@RequestMapping( uri.main )
	public void home( @RequestParam(value = "user_id") String user_id,
			HttpSession session, HttpServletResponse response) throws IOException {
		
		/*
		 * checar usuario autenticado, caso nao redirecionar dar signUp
		 * checar mecanismos de seguran�a : considerar spring-security
		 */
		
		// TODO refact to set request scope
		UserSocialInfo user = (UserSocialInfo) session.getAttribute( user_id );
		String message = "welcome " + user.name + " : " + user.email;
		String urlPhoto = uri.photo + "?access_token=" + user.oauth_token;
		String urlMessage = "";
		display(response, message, urlPhoto, urlMessage);
	}
	
	private void display(HttpServletResponse response, String content, 
			String urlPhoto, String urlMessage) throws IOException {;
			
		response.setContentType(MimeType.text_html.type);
		
		StringBuilder builder = new StringBuilder();
		builder.append( "<html><body>" );
		
		builder.append( "<form>" );
		builder.append( content + "<br/><br/>");
		builder.append( "click to see your avatar <a href='" + urlPhoto + "'>here</a>" );
		builder.append( "<br/><br/>");
		builder.append( "Enter your message <input type='text' name='message'/>" );
		builder.append( "<input type='submit' value='Send'/>" );
		
		builder.append( "</form>" );
		builder.append( "<body></html>" );
		
		response.getWriter().write( builder.toString() );
		response.getWriter().flush();
		
	}
	
}