package br.eng.mosaic.pigeon.server.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.eng.mosaic.pigeon.common.domain.User;
import br.eng.mosaic.pigeon.common.dto.UserInfo;
import br.eng.mosaic.pigeon.server.service.UserService;
import br.eng.mosaic.pigeon.server.socialnetwork.FacebookClient;

@Controller
public class FacebookController {
	
	protected interface uri {
		String redir = "redirect:";
		String main = "/home.do";
		String redirMain = redir + main;
		String start = "oauth/facebook/start.do";
		String callback = "oauth/facebook/callback.do";
	}
	
	protected interface alias {
		String user = "user";
	}
	
	@Autowired private FacebookClient client;
	@Autowired private UserService userService;
	
	@RequestMapping( uri.start )
	public String start(HttpSession session) {	
		return uri.redir + client.getStartConnection( uri.callback );
	}
	
	@RequestMapping( uri.callback )
	public ModelAndView callback( @RequestParam(value="code") String hash, 
			HttpSession session) throws IOException {
		
		ModelAndView view = new ModelAndView( uri.redirMain );
		if (hash	 == null || hash.isEmpty()) return view;
		
		UserInfo userInfo = getUser( hash );
		User user = userService.connect( userInfo );

		// TODO refact to set request scope
		session.setAttribute(alias.user, user);
		return view;
	}
	
	private UserInfo getUser(String hash) {
		String token = client.getAccessTokenFromUser( uri.callback, hash );
		return client.getBasicUserInfo( uri.callback, token );
	}
	
}