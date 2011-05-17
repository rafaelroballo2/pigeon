package br.eng.mosaic.pigeon.server.socialnetwork;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import br.eng.mosaic.pigeon.server.integration.PigeonConfiguration;

public abstract class SocialNetworkResolver {

	private static final String defaultEncoding = "UTF-8";
	private static final String formatJSON = "&format=json";
	private static final String urlApiQuery = "https://api.facebook.com/method/fql.query?query=";
	private static final String concatToken = "&access_token=";
	
	abstract public String getApplicationInfo(String fbuid, String accessToken);
	abstract public String getApplicationCredentials();
	
	protected PigeonConfiguration pigeonConfig;
	
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
	
	protected enum ScopePermission {
		fb_email("email"),
		fb_about_user("user_about_me"), 
		fb_publish_stream("publish_stream");
		
		private ScopePermission(String key) {
			this.key = key;
		}
		
		public static String all() {
			String scopes = "";
			for (ScopePermission scope : values()) {
				scopes += scope.key + ",";
			}
			return scopes.substring(0, scopes.length() -1);
		}
		
		public static String about_user() {
			return fb_email.key + "," + fb_about_user.key;
		}
		
		public String key;
	}
	
	protected enum RequestMethod {
		fb_user_info("me"), 
		fb_user_publish("me/feed"),
		fb_user_picture("me/picture"), 
		fb_oauth_authorize("oauth/authorize"),
		fb_oauth_access_token("oauth/access_token"); 
		
		private RequestMethod(String method) {
			this.method = method;
		}
		
		public String method;
		
		public String replace(String fbuid) {
			return method.replace("me", fbuid);
			
		}
	}	
	
	public void setPigeonConfig(PigeonConfiguration pigeonConfig) {
		this.pigeonConfig = pigeonConfig;
	}
	
}