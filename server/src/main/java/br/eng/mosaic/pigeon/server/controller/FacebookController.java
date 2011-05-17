package br.eng.mosaic.pigeon.server.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.eng.mosaic.pigeon.common.domain.SocialNetwork.Social;
import br.eng.mosaic.pigeon.common.dto.UserInfo;
import br.eng.mosaic.pigeon.server.controller.HomeController.uri;
import br.eng.mosaic.pigeon.server.helper.MimeType;
import br.eng.mosaic.pigeon.server.service.UserService;
import br.eng.mosaic.pigeon.server.socialnetwork.FacebookClient;

@Controller
public class FacebookController extends AbstractSocialController {
	
	protected interface uri_fb {
		String sign_in = "oauth/facebook/signIn.do";
		String sign_callback = "oauth/facebook/callback.do";
		String photo = "{user_id}/oauth/facebook/photo.do";
		String publish = "{user_id}/oauth/facebook/publish.do";
	}

	@Autowired private FacebookClient client;
	@Autowired private UserService userService;

	@RequestMapping( uri_fb.sign_in )
	public String sign_in(HttpSession session) throws MalformedURLException {
		return uri.redir + client.getUrlCodeKnowUser(uri_fb.sign_callback);
	}
	
	@RequestMapping( uri_fb.sign_callback )
	public void callback( @RequestParam(value = "code") String hash,
			HttpSession session, HttpServletResponse response ) throws IOException {

		if ( hash == null || hash.isEmpty() )
			ack_error(response, "erro ao autenticar com server facebook");

		UserInfo user = client.getUser(uri_fb.sign_callback, hash);
		userService.connect(user);
		session.setAttribute(user.email, user);
		
		ack_ok(response, user.email);
	}

	@RequestMapping( uri_fb.photo )
	public void photo( @PathVariable String user_id, HttpSession session, HttpServletResponse response) 
			throws ClientProtocolException, URISyntaxException, IOException {

		UserInfo user = getUser(session, user_id);
		String token = user.get( Social.facebook ).token;

		byte[] photo = client.getPicture( token );
		download(response, MimeType.image_png, photo );
	}
	
	@RequestMapping( uri_fb.publish )
	public void publish( @PathVariable String user_id, HttpSession session, HttpServletResponse response,
			@RequestParam(value = "message") String message ) throws IOException, URISyntaxException {
		
		UserInfo user = getUser(session, user_id);
		String doc_id = client.publish(user, message);
		ack_ok(response, doc_id);
	}
	
}