package br.eng.mosaic.pigeon.server.socialnetwork;

import java.net.URI;
import java.net.URISyntaxException;

public class FacebookResolver extends SocialNetworkResolver {
	
	private FacebookConfiguration fbConfig;
	
	public String getCredentials() {
		String command = RequestMethod.fb_oauth_access_token.method
			+ "?client_secret=" + fbConfig.secret
			+ "&client_id=" + fbConfig.id
			+ "&grant_type=" + "client_credentials";
	
		return mount( command );
	}
	
	public String getCodeKnownUser(String callback, String scope) {
		String cURLCallback = pigeonConfig.app_root + callback; 
		String command = RequestMethod.fb_oauth_authorize.method 
			+ "?client_id=" + fbConfig.id 
			+ "&redirect_uri=" + cURLCallback + "?" 
			+ "&scope=" + scope;
		
		return mount( command );
	}
	
	public String getAccessToken(String callback, String hash) {
		String callbackUrl = pigeonConfig.app_root + callback;
		String command = RequestMethod.fb_oauth_access_token.method
			+ "?client_id=" + fbConfig.id
			+ "&client_secret=" + fbConfig.secret
			+ "&code=" + hash
			+ "&redirect_uri=" + callbackUrl;
		
		return mount( command );		
	}
	
	public String getUserInfo(String callbackUri, String token) {
		String commandToken = ResponseAttribute.fb_access_token.key + token; 
		String command = RequestMethod.fb_user_info.method 
			+ "?" + commandToken
			+ "&client_id=" + fbConfig.id
			+ "&redirect_uri=" + pigeonConfig.app_root 
			+ callbackUri;
		
		return mount( command );
	}
	
	public URI getUserPicture(String token) throws URISyntaxException {
		String commands = "type=small&" 
			+ ResponseAttribute.fb_access_token.key + token;
		
		String request = "/" + RequestMethod.fb_user_picture.method;
		return new URI("https", "graph.facebook.com", request, commands, null);
	}
	
	public URI postMessage(String message, String fbuid, String token) throws URISyntaxException {
		String request = "/" + RequestMethod.fb_user_publish.replace(fbuid);
		String commandToken = ResponseAttribute.fb_access_token.key + token;
		
		String commands = commandToken 
			+ "&message=" + message
			+ "&description=teste_teste_teste";
		
		return new URI("https", "graph.facebook.com", request, commands, null);
	}
	
	public void setFbConfig(FacebookConfiguration fbConfig) {
		this.fbConfig = fbConfig;
	}
	
	protected String mount( String command ) {
		return pigeonConfig.fb_root + command;
	}
	
	protected URI mountUri( String command ) {
		return URI.create( pigeonConfig.fb_root + command );
	}

}