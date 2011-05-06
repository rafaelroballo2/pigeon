package br.eng.mosaic.pigeon.server.socialnetwork;

import br.eng.mosaic.pigeon.server.integration.PigeonConfiguration;

public class FacebookResolver extends SocialNetworkResolver {
	
	protected PigeonConfiguration appConfig;
	protected FacebookConfiguration fbConfig;
	
	public FacebookResolver() {
		appConfig = new PigeonConfiguration();
		fbConfig = new FacebookConfiguration();
	}
	
	protected String mount( String command ) {
		return appConfig.app_root + command;
	}
	
	@Deprecated @Override public String getApplicationInfo(String fbuid, String accessToken) {
		String query = 
			"SELECT display_name, description " +
				"FROM application WHERE app_id= :param1";
		return fetch(query, accessToken, -1, fbuid);
	}
	
	public String getAccessTokenFromApplication() {
		String command = RequestMethod.fb_oauth_access_token.method
			+ "?client_secret=" + fbConfig.keySecret
			+ "&client_id=" + fbConfig.id
			+ "&grant_type=" + "client_credentials";
	
		return mount( command );
	}
	
	public String getUrlStartConnection(String callback) {
		String cURLCallback = appConfig.app_root + callback; 
		String command = RequestMethod.fb_oauth_authorize.method 
			+ "?client_id=" + fbConfig.id 
			+ "&redirect_uri=" + cURLCallback + "?" 
			+ "&scope=email,user_about_me";
		return mount( command );
	}
	
	public String getAccessTokenFromUser(String callback, String hash) {
		String callbackUrl = appConfig.app_root + callback;
		String command = RequestMethod.fb_oauth_access_token.method
			+ "?client_id=" + fbConfig.id
			+ "&client_secret=" + fbConfig.keySecret
			+ "&code=" + hash 
			+ "&redirect_uri=" + callbackUrl;
		
		return mount( command );
	}
	
	public String getBasicUserInfo(String token) {
		String command = RequestMethod.fb_oauth_access_token.method
			+ ResponseAttribute.fb_access_token.key 
			+ token;
		return mount( command );
	}
	
	public String getPictureFromUser(String token) {
		String command = RequestMethod.fb_user_picture.method
			+ "?type=large"
			+"&" + ResponseAttribute.fb_access_token.key 
			+ token;
		return mount( command );
	}
	
}