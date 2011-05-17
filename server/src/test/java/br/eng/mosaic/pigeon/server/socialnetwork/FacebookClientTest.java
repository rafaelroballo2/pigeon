package br.eng.mosaic.pigeon.server.socialnetwork;

import static br.eng.mosaic.pigeon.server.helper.MimeType.text_json;
import static br.eng.mosaic.pigeon.server.helper.MimeType.text_plain;
import static br.eng.mosaic.pigeon.server.socialnetwork.SocialNetworkResolver.ResponseAttribute.fb_access_token;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

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

import br.eng.mosaic.pigeon.common.domain.SocialNetwork.Social;
import br.eng.mosaic.pigeon.common.dto.UserInfo;
import br.eng.mosaic.pigeon.common.dto.UserInfo.SocialInfo;
import br.eng.mosaic.pigeon.server.exception.ServerCrashException;
import br.eng.mosaic.pigeon.server.helper.MimeType;
import br.eng.mosaic.pigeon.server.helper.ServerFakeLocal;
import br.eng.mosaic.pigeon.server.integration.PigeonConfiguration;
import static br.eng.mosaic.pigeon.server.socialnetwork.SocialNetworkResolver.RequestMethod.fb_oauth_access_token;
import static br.eng.mosaic.pigeon.server.socialnetwork.SocialNetworkResolver.RequestMethod.fb_user_info;
import br.eng.mosaic.pigeon.server.socialnetwork.SocialNetworkResolver.RequestMethod;
import br.eng.mosaic.pigeon.server.socialnetwork.SocialNetworkResolver.ScopePermission;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:br/eng/mosaic/pigeon/cfg/spring/spring-test-beans.xml" })
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class FacebookClientTest extends TestCase {
	
	private ServerFakeLocal fakeServer;
	private static Random random;
	
	@Resource private PigeonConfiguration pigeonConfig;
	@Autowired private FacebookClient facebookClient;
	
	@BeforeClass public static void beforeAll() {
		random = new Random();
	}
	
	private synchronized void startServer(MimeType mime, String content) {
		prepareLocalServer(mime, null, content);
	}
	
	private synchronized void startServer(MimeType mime, Map<String, String> map) {
		prepareLocalServer(mime, map, null);
	}
	
	private void prepareLocalServer(MimeType mime, Map<String, String> map, String content) {
		try {
			this.fakeServer = new ServerFakeLocal(mime, map, content);
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
			obj.put("id"	, "807235872353");
			obj.put("name"	, "rafa");
			obj.put("email"	, "faelcavalcanti@gmail.com");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return obj;
	}

	@Test public void testGetTokenApplicationSuccessfully() {
		String token = String.valueOf( random.nextInt(1000001) );
		startServer(text_plain, fb_access_token.key + token );
		
		String found = facebookClient.getTokenApplication();
		assertNotNull( token );
		assertNotSame("obviously that objects aren't same", token, found);
		assertEquals( token , found);
		stopServer();
	}
	
	@Test(expected=ServerCrashException.class) public void testGetTokenApplicationWhenServerCrash() {
		facebookClient.getTokenApplication();
	}
	
	@Test public void testGetUrlCodeKnowUserSuccessfully() throws IOException {
		String callback = "callback.do";
		String cURL = facebookClient.getUrlCodeKnowUser( callback );
		
		assertNotNull( cURL );
		assertTrue( cURL.contains( ScopePermission.all() ) );
		assertTrue( cURL.contains( callback ) );
		
		URI uri = URI.create( cURL );
		assertEquals( RequestMethod.fb_oauth_authorize.method, uri.getPath().substring(1) );
		
		String query = cURL.split("\\?")[1] + "?&scope=" + ScopePermission.all();
		assertEquals( uri.getQuery(), query );
		assertTrue( cURL.startsWith( pigeonConfig.fb_root ) );		
		
		String param = "redirect_uri=";
		int initIndex = cURL.indexOf( param ); 
		int finalIndex = initIndex + param.length() + pigeonConfig.app_root.length() + callback.length();
		String redirect_uri = cURL.substring(initIndex + param.length(), finalIndex);
		assertEquals( redirect_uri , pigeonConfig.app_root + callback);
	}
	
	@Test(expected=MalformedURLException.class) 
	public void testGetUrlCodeKnowUserWhenCallbackNullable() throws MalformedURLException {
		facebookClient.getUrlCodeKnowUser( null );
	}
	
	@Test public void testGetUserSuccessfully() throws JSONException {
		JSONObject json = createBasicUserInfo();
		String prefixToken = "access_token=";
		String token = prefixToken + String.valueOf( random.nextInt(1000001) );
		
		Map<String,String> map = new HashMap<String, String>();
		map.put(fb_oauth_access_token.method, token);
		map.put(fb_user_info.method, json.toString());
		
		startServer(text_json, map);
		
		UserInfo user = facebookClient.getUser( "whatever.thing.this", token );
		assertNotNull( user );
		
		assertEquals( user.name , json.getString("name") );
		assertEquals( user.email , json.getString("email") );
		
		SocialInfo social = user.get( Social.facebook );
		assertEquals( social.id , json.getString("id") );
		assertEquals( social.token , token.substring( prefixToken.length() ) );
		
		stopServer();
	}
	
	/*
	 * ainda a testar com getUser
	 * 		token nao estar no server (tagger pode dar erro)
	 * 		access_token= (mudou)
	 * 		
	 * 		json nao esta no server
	 * 		response nao veio
	 */
	
	@Test(expected=ServerCrashException.class) public void testGetUserWhenServerCrash() {
		facebookClient.getUser( "whatever.thing.this", null );
	}
	
	@Test public void testGetUserWhenTokenNotAvailableOnServer() {
		String prefix = "access_token=";
		String token = prefix + String.valueOf( random.nextInt(1000001) );
		startServer(text_json, token.replace(prefix, "nothing") );
		
		facebookClient.getUser( "whatever.thing.this", null );
		stopServer();
	}
	
}