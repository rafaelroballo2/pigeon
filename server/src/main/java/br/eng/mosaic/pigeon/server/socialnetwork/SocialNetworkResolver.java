package br.eng.mosaic.pigeon.server.socialnetwork;

import br.eng.mosaic.pigeon.server.integration.PigeonConfiguration;

public abstract class SocialNetworkResolver {

	abstract public String getCredentials();
	
	protected PigeonConfiguration pigeonConfig;
	
	protected enum ResponseAttribute {
		fb_access_token("access_token=");
		
		private ResponseAttribute(String key) {
			this.key = key;
		}
		
		public String key;
	}
	
	public enum ScopePermission {
		fb_email("email"),
		fb_about_user("user_about_me"), 
		fb_publish_stream("publish_stream");
		
		private ScopePermission(String key) {
			this.key = key;
		}
		
		public static String all() {
			StringBuilder builder = new StringBuilder();
			for (ScopePermission scope : values()) {
				builder.append( scope.key + "," );
			}
			String scopes = builder.toString(); 
			return scopes.substring(0, scopes.length() - 1 );
		}
		
		public static String about_user() {
			return fb_email.key + "," + fb_about_user.key;
		}
		
		public String key;
	}
	
	public enum RequestMethod {
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