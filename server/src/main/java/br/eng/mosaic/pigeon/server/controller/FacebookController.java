package br.eng.mosaic.pigeon.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.eng.mosaic.pigeon.common.dto.UserInfo;
import br.eng.mosaic.pigeon.infra.exception.NotImplemetedYetException;
import br.eng.mosaic.pigeon.server.socialnetwork.FacebookClient;

@Controller
public class FacebookController {
	
	@Autowired private FacebookClient client;
	
	@RequestMapping("/**/pigeon/oauth/facebook/start.do") 
	public String start() {
		String callback = "";
		return "redirect:" + client.getStartConnection(callback);
	}
	
	@RequestMapping("/**/pigeon/oauth/facebook/connect.do") 
	public ModelAndView callback(
			@RequestParam(value="code") String code	) {
		
		ModelAndView view = new ModelAndView("");
		UserInfo user = client.getBasicUserInfo( code );
		
		throw new NotImplemetedYetException();
	}
	
	

}