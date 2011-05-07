package br.eng.mosaic.pigeon.server.socialnetwork;

import static br.eng.mosaic.pigeon.server.helper.MimeType.text_json;
import static br.eng.mosaic.pigeon.server.helper.MimeType.text_plain;
import static br.eng.mosaic.pigeon.server.socialnetwork.SocialNetworkResolver.ResponseAttribute.fb_access_token;

import java.io.IOException;
import java.util.Random;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import br.eng.mosaic.pigeon.common.dto.UserInfo;
import br.eng.mosaic.pigeon.server.helper.MimeType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:br/eng/mosaic/pigeon/cfg/spring/spring-test-beans.xml" })
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class FacebookClientTest extends TestCase {
	
	private FacebookServerFake fakeServer;
	private static Random random;
	
	@Autowired private FacebookClient facebookClient;
	
	@BeforeClass public static void beforeAll() {
		random = new Random();
	}
	
	private synchronized void startServer(final MimeType mime, final String content) {
		try {
			this.fakeServer = new FacebookServerFake(mime, content);
		} catch (IOException e) {
			String message = "falhou ao levantar server : checa isso > \n" + e.getMessage(); 
			throw new RuntimeException( message );
		}
	}
	
	private synchronized void stopServer() {
		this.fakeServer.stop();
	}
	
	private JSONObject createBasicUserInfo() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("name", "rafa");
			obj.put("email", "faelcavalcanti@gmail.com");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return obj;
	}

	@Test public void testGetAccessTokenFromAppSuccessfully() {
		String token = String.valueOf( random.nextInt(1000001) );
		startServer(text_plain, fb_access_token.key + token );
		
		String found = facebookClient.getAccessTokenFromApplication();
		assertNotNull( token );
		assertNotSame("obviously that objects aren't same", token, found);
		assertEquals( token , found);
		stopServer();
	}
	
	/*
	 * casos ainda a testar 
	 * 		fb fora do ar
	 * 		fb mudou url 
	 * 		fb por algum motivo nao entregou o token
	 * a implementar
	 * 		alem do token, retornar tb o timeout.session (DTO)
	 */
	
	@Test public void testGetCommandStartConnectionSuccessfully() {
		String callback = "oauth/facebook/connect.do";
		String cURL = facebookClient.getStartConnection( callback );
		
		assertNotNull( cURL );
		assertTrue( cURL.contains( callback ) );
	}
	
	@Test public void testGetAccessTokenFromUserSuccessfully() {
		String token = String.valueOf( random.nextInt(1000001) );
		startServer(text_plain, fb_access_token.key + token );
		
		String callback = "oauth/facebook/connect.do";
		String hash = String.valueOf( random.nextInt(100) );
		String found = facebookClient.getAccessTokenFromUser( callback, hash );
		
		assertNotNull( token );
		assertEquals( token , found);
		stopServer();
	}
	
	/*
	 * casos ainda a testar
	 * 		os mesmos levantados anteriormente para o metodo
	 * 			testGetAccessTokenFromAppSuccessfully()
	 */
	@Test public void testGetUserInfoSuccessfully() throws JSONException {
		JSONObject obj = createBasicUserInfo();
		String token = String.valueOf( random.nextInt(1000001) );
		startServer(text_json, obj.toString() );
		
		String callback = "oauth/facebook/connect.do";
		UserInfo user = facebookClient.getBasicUserInfo( callback, token );
		
		assertNotNull( user );
		assertEquals( user.name, obj.getString("name"));
		assertEquals( user.email, obj.getString("email"));
		stopServer();
	}
	
	// TODO ultimo pegar avatar
	
}