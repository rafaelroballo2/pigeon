package br.eng.mosaic.pigeon.server.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.eng.mosaic.pigeon.common.dto.UserSocialInfo;
import br.eng.mosaic.pigeon.server.helper.MimeType;
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
		String photo = "oauth/facebook/photo.do";
	}

	protected interface alias {
		String user = "user";
	}

	@Autowired
	private FacebookClient client;
	@Autowired
	private UserService userService;

	@RequestMapping(uri.start)
	public String start(HttpSession session) {
		return uri.redir + client.getStartConnection(uri.callback);
	}
	
	@RequestMapping( "oauth/facebook/starter.do" )
	public String starter( @RequestParam(value = "accessToken") String token,
			HttpServletResponse response) throws ClientProtocolException,
					URISyntaxException, IOException {
		System.out.println( "ueape" );
		return "";
	}

	@RequestMapping(uri.callback)
	public ModelAndView callback(@RequestParam(value = "code") String hash,
			HttpSession session) throws IOException {

		ModelAndView view = new ModelAndView(uri.redir + "/");
		if (hash == null || hash.isEmpty())
			return view;

		UserSocialInfo userInfo = getUser(hash);
		userService.connect(userInfo);

		// TODO refact to set request scope
		session.setAttribute(userInfo.email, userInfo);
		return new ModelAndView(uri.redirMain + "?user_id=" + userInfo.email );
	}

	@RequestMapping( uri.photo )
	public void photo( @RequestParam(value = "access_token") String token,
			HttpServletResponse response) throws ClientProtocolException,
				URISyntaxException, IOException {

		byte[] photo = client.getPicture(token);
		downPhoto(response, photo );
	}

	private void downPhoto(HttpServletResponse response, byte[] bytes) throws IOException {
		response.setContentType("image/png");
		OutputStream out = response.getOutputStream();
		out.write( bytes );
	}

	private UserSocialInfo getUser(String hash) {
		String token = client.getAccessTokenFromUser(uri.callback, hash);
		return client.getBasicUserInfo(uri.callback, token);
	}

}