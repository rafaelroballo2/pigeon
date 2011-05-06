package br.eng.mosaic.pigeon.server.socialnetwork;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public abstract class SocialNetworkResolver {

	private static final String defaultEncoding = "UTF-8";
	private static final String formatJSON = "&format=json";
	private static final String urlApiQuery = "https://api.facebook.com/method/fql.query?query=";
	private static final String concatToken = "&access_token=";
	
	abstract public String getApplicationInfo(String fbuid, String accessToken);
	abstract public String getAccessTokenFromApplication();
	
	protected String fetch(String query, String token, int pageSize, String ... params) {
		String partialQuery = null;
		try {
			partialQuery = URLEncoder.encode(partialQuery, defaultEncoding);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} 
		return urlApiQuery + partialQuery + formatJSON + concatToken + token;
	}
	
	protected enum ResponseAttribute {
		fb_access_token("access_token=");
		
		private ResponseAttribute(String key) {
			this.key = key;
		}
		
		public String key;
	}
	
	protected enum RequestMethod {
		fb_oauth_access_token("oauth/access_token"),
			fb_oauth_authorize("oauth/authorize"),
			fb_user_picture("me/picture"),
			fb_user_info("me");
		
		private RequestMethod(String method) {
			this.method = method;
		}
		
		public String method;
	}	
	
}