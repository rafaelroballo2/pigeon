package br.eng.mosaic.pigeon.server.controller;

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
		String redirect = "redirect:";
		String main = redirect + "/home.do";
		String context = "/**/pigeon/"; 
		String starter = context + "oauth/facebook/start.do";
		String connect = context + "oauth/facebook/connect.do";
	}
	
	protected interface alias {
		String user = "user";
	}
	
	@Autowired private FacebookClient client;
	@Autowired private UserService userService;
	
	@RequestMapping( uri.starter ) 
	public String start() {
		return uri.redirect + client.getStartConnection( uri.connect );
	}
	
	@RequestMapping( uri.connect ) 
	public ModelAndView callback( @RequestParam(value="code") String code, HttpSession session ) {
		
		ModelAndView view = new ModelAndView( uri.main );
		if (code == null || code.isEmpty()) return view;
		
		UserInfo userInfo = this.getUser(code);
		User user = userService.connect( userInfo );
		
		session.setAttribute(alias.user , user);
		return view;
	}
	
	private UserInfo getUser(final String code) {
		String token = client.getAccessTokenFromUser(uri.connect, code);
		return client.getBasicUserInfo( token );
	}
	
}