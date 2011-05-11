package br.eng.mosaic.pigeon.server.socialnetwork;

public class FacebookResolver extends SocialNetworkResolver {
	
	protected FacebookConfiguration config;
	
	public FacebookResolver() {
		config = new FacebookConfiguration();
	}
	
	protected String mount( String command ) {
		return config.root + command;
	}
	
	@Deprecated @Override public String getApplicationInfo(String fbuid, String accessToken) {
		String query = 
			"SELECT display_name, description " +
				"FROM application WHERE app_id= :param1";
		return fetch(query, accessToken, -1, fbuid);
	}
	
	public String getAccessTokenFromApplication() {
		String command = RequestMethod.fb_oauth_access_token.method
			+ "?client_secret=" + config.keySecret
			+ "&client_id=" + config.id
			+ "&grant_type=" + "client_credentials";
	
		return mount( command );
	}
	
	public String getUrlStartConnection(String callback) {
		String cURLCallback = getContext() + callback; 
		String command = RequestMethod.fb_oauth_authorize.method 
			+ "?client_id=" + config.id 
			+ "&redirect_uri=" + cURLCallback + "?" 
			+ "&scope=email,user_about_me";
		return mount( command );
	}
	
	public String getAccessTokenFromUser(String callback, String hash) {
		String callbackUrl = getContext() + callback;
		String command = RequestMethod.fb_oauth_access_token.method
			+ "?client_id=" + config.id
			+ "&client_secret=" + config.keySecret
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