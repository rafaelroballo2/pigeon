package br.eng.mosaic.pigeon.server.socialnetwork;

import static br.eng.mosaic.pigeon.server.helper.MimeType.text_json;
import static br.eng.mosaic.pigeon.server.helper.MimeType.text_plain;
import static br.eng.mosaic.pigeon.server.helper.Miscellany.getJSONSample;
import static br.eng.mosaic.pigeon.server.socialnetwork.SocialNetworkResolver.RequestMethod.fb_oauth_access_token;
import static br.eng.mosaic.pigeon.server.socialnetwork.SocialNetworkResolver.RequestMethod.fb_user_info;
import static br.eng.mosaic.pigeon.server.socialnetwork.SocialNetworkResolver.ResponseAttribute.fb_access_token;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import br.eng.mosaic.pigeon.common.domain.SocialNetwork.Social;
import br.eng.mosaic.pigeon.common.dto.UserInfo;
import br.eng.mosaic.pigeon.common.dto.UserInfo.SocialInfo;
import br.eng.mosaic.pigeon.server.exception.ServerCrashException;
import br.eng.mosaic.pigeon.server.exception.ServerUnknownResourceException;
import br.eng.mosaic.pigeon.server.helper.MimeType;
import br.eng.mosaic.pigeon.server.helper.Miscellany;
import br.eng.mosaic.pigeon.server.helper.ServerFakeLocal;
import br.eng.mosaic.pigeon.server.integration.PigeonConfiguration;
import br.eng.mosaic.pigeon.server.socialnetwork.SocialNetworkResolver.RequestMethod;
import br.eng.mosaic.pigeon.server.socialnetwork.SocialNetworkResolver.ScopePermission;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:br/eng/mosaic/pigeon/cfg/spring/spring-test-beans.xml" })
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class FacebookClientTest extends TestCase {
	
	private ServerFakeLocal fakeServer;
	
	@Autowired private PigeonConfiguration pigeonConfig;
	@Autowired private FacebookClient facebookClient;
	
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
	
	private Map<String, String> getMapping(String first, JSONObject second) {
		Map<String,String> map = new HashMap<String, String>();
		map.put(fb_oauth_access_token.method, first);
		map.put(fb_user_info.method, second.toString());
		return map;
	}
	
	@Test public void testGetTokenApplicationSuccessfully() {
		String token = Miscellany.getNumber();
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
		String token = Miscellany.getNumber();
		JSONObject json = Miscellany.getJSONUser();
		startServer(text_json, getMapping(fb_access_token.key + token, json));
		
		UserInfo user = facebookClient.getUser( "whatever.thing.this", token );
		assertNotNull( user );
		
		assertEquals( user.name , json.getString("name") );
		assertEquals( user.email , json.getString("email") );
		
		SocialInfo social = user.get( Social.facebook );
		assertEquals( social.id , json.getString("id") );
		assertEquals( social.token , token );
		
		stopServer();
	}
	
	@Test(expected=ServerCrashException.class) public void testGetUserWhenServerCrash() {
		facebookClient.getUser( "whatever.thing.this", null );
	}	
	
	@Test public void testGetUserWhenTokenUnknownOnServer() {
		startServer(text_json, "nothing" );
		boolean behavior = false;
		try {
			facebookClient.getUser( "whatever.thing.this", null );
		} catch (ServerUnknownResourceException e) {			
			behavior = true; // sucess
		} 
		assertTrue( behavior );
		stopServer();
	}
	
	@Test public void testGetUserWhenBasicUserInfoNotAvailableOnServer() throws JSONException {
		startServer(text_json, getMapping( "", new JSONObject()));
		boolean behavior = false;
		try {
			facebookClient.getUser( "whatever.thing.this", "" );
		} catch (ServerUnknownResourceException e) {			
			behavior = true; // sucess
		} 
		assertTrue( behavior );
		stopServer();
	}
	
	@Test public void testGetUserWhenBasicUserInfoChangedOnServer() throws JSONException {
		String token = Miscellany.getNumber();
		startServer(text_json, getMapping(token, getJSONSample()));
		boolean behavior = false;
		
		try {
			facebookClient.getUser( "whatever.thing.this", token );
		} catch (ServerUnknownResourceException e) {			
			behavior = true; // sucess
		} 
		assertTrue( behavior );
		stopServer();
	}
	
	@Test public void testPublishSuccessfully() throws JSONException {
		String token = fb_access_token.key + Miscellany.getNumber();
		JSONObject json = Miscellany.getJSONUser();
		startServer(text_json, getMapping(token, json));
		
		UserInfo user = facebookClient.getUser( "whatever.thing.this", token );
		String response = facebookClient.publish(user, "teste 123 mensagem 456");
		
		assertNotNull( response );
		
		stopServer();
	}
	
	@Ignore @Test public void testPublishWhenMessageNullable() {
		throw new NotImplementedException();
	}

	@Ignore @Test public void testPublishWhenMessageGreaterThan140Characters() {
		throw new NotImplementedException();
	}
	
	@Ignore @Test public void testPublishWhenReceivedInvalidToken() {
		throw new NotImplementedException();
	}
	
	@Ignore @Test public void testPublishWhenTokenExpired() {
		throw new NotImplementedException();
	}
	
	// TODO se mandar url com outro email dah nullPont pq nao existe na session, tratar isso
	// isso fica no controller, ele nao deveria deixar passar!
	
}