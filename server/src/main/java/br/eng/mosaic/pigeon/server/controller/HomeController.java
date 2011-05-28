package br.eng.mosaic.pigeon.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.eng.mosaic.pigeon.common.dto.UserInfo;
import br.eng.mosaic.pigeon.server.helper.MimeType;

@Controller
public class HomeController {
	
	protected interface uri {
		String main = "/home.do";
		String redir = "redirect:";
		String redirMain = redir + main;
	}
	
	@RequestMapping( uri.main )
	public void home( @RequestParam(value = "user_id") String user_id,
			HttpSession session, HttpServletResponse response) throws IOException {
		
		// checar usuario autenticado, caso nao redirecionar dar signUp
		// checar mecanismos de seguranca : considerar spring-security
		
		UserInfo user = (UserInfo) session.getAttribute( user_id );
		String token = user.socials.get(0).token;
		
		String message = "welcome " + user.name + " : " + user.email;
		String urlPhoto = "uri_fb.photo" + "?access_token=" + token;
		display(response, message, urlPhoto, "uri_fb.testSendMessage", token);
		
		session.setAttribute( "access_token", token );
	}
	
	private void display(HttpServletResponse response, String content, 
			String urlPhoto, String urlMessage, String token) throws IOException {
			
		response.setContentType(MimeType.text_html.type);
		
		StringBuilder builder = new StringBuilder();
		builder.append( "<html><body>" );
		
		builder.append( "<form action='" + urlMessage + "' >" );
		builder.append( content + "<br/><br/>");
		builder.append( "click to see your avatar <a href='" + urlPhoto + "'>here</a>" );
		builder.append( "<br/><br/>");
		builder.append( "<input type='hidden' name='access_token' value='" + token + "'/>" );
		
		builder.append( "click to send test message <a href='" + "uri_fb.testSendMessage" + "'>here</a>" );
		builder.append( "</form>" );
		builder.append( "<body></html>" );
		
		response.getWriter().write( builder.toString() );
		response.getWriter().flush();
		
	}
	
	@RequestMapping( "uri_fb.testSendMessage" )
	public void homeSendMessage( @RequestParam(value = "code") String code,
			HttpServletResponse response, HttpSession session ) throws IOException {
		
		response.setContentType(MimeType.text_html.type);
		
		StringBuilder builder = new StringBuilder();
		builder.append( "<html><body>" );
		
		builder.append( "<form action='" + "uri_fb.testSendMessage" + "' >" );
		builder.append( "<br/><br/>");
		
		builder.append( "send your test message " );
		builder.append( "<input type='text' name='message' />" );
		
		builder.append( "<input type='submit' value='send'/>" );
		
		String token = (String) session.getAttribute("access_token");
		
		builder.append( "<input type='hidden' name='code' value='" + token + "'/>" );
		builder.append( "</form>" );
		builder.append( "<body></html>" );
		
		response.getWriter().write( builder.toString() );
		response.getWriter().flush();
	}
	
}