package br.eng.mosaic.pigeon.server.socialnetwork;

import static br.eng.mosaic.pigeon.common.domain.SocialNetwork.Social.facebook;
import static br.eng.mosaic.pigeon.server.socialnetwork.SocialNetworkResolver.ResponseAttribute.fb_access_token;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import br.eng.mosaic.pigeon.common.domain.SocialNetwork.Social;
import br.eng.mosaic.pigeon.common.dto.UserInfo;
import br.eng.mosaic.pigeon.server.exception.ServerUnavailableResourceException;
import br.eng.mosaic.pigeon.server.exception.ServerUnknownResourceException;
import br.eng.mosaic.pigeon.server.helper.IOFetchContent;
import br.eng.mosaic.pigeon.server.socialnetwork.SocialNetworkResolver.ResponseAttribute;
import br.eng.mosaic.pigeon.server.socialnetwork.SocialNetworkResolver.ScopePermission;

public class FacebookClient {
	
	private IOFetchContent ioFetch;
	private Tagger tagger;
	private FacebookResolver resolver;
	
	public FacebookClient() {
		ioFetch = new IOFetchContent();
		tagger = new Tagger();
	}
	
	public String getTokenApplication() {
		String cURL = resolver.getCredentials();
		String response = ioFetch.getContent( cURL );
		return tagger.get(response, fb_access_token);
	}
	
	public String getUrlCodeKnowUser(String callback) throws MalformedURLException {
		if ( callback == null || callback.isEmpty() )
			throw new MalformedURLException();			
		return resolver.getCodeKnownUser( callback, ScopePermission.all() );
	}
	
	private String getToken(String callback, String hash) {
		String cURL = resolver.getAccessToken(callback, hash);
		String response = ioFetch.getContent( cURL );
		return tagger.getAccessToken( response );
	}
	
	public UserInfo getUser(String callback, String hash) {
		String token = getToken(callback, hash);
		String cURL = resolver.getUserInfo( callback, token );
		String response = ioFetch.getContent( cURL );
		JSONObject json = tagger.getJSONObject( response );
		return createUser(json, token);
	}
	
	private UserInfo createUser(JSONObject json, String token) {
		try {
			String id = json.getString("id");
			String name = json.getString("name");
			String email = json.getString("email");
			
			UserInfo user = new UserInfo(name, email);
			user.add(facebook, id, token);
			return user;
		} catch (JSONException e) {
			throw new ServerUnknownResourceException();
		}
	}
	
	public byte[] getPicture(String token) throws ClientProtocolException, URISyntaxException, IOException {
		URI uri = resolver.getUserPicture( token );
		return ioFetch.getStream( uri );
	}
	
	public String publish(UserInfo user, String message) {

		String fbuid = user.get( Social.facebook ).id;
		String token = user.get( Social.facebook ).token;
		
		String signal = "fail";
		try {
			URI uri = resolver.postMessage(message, fbuid, token);
			signal = ioFetch.getHttpClientContent( uri );
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		
		return signal;
	}
	
	class Tagger {
		
		private boolean isInvalid( String response, ResponseAttribute alias ) {
			if ( response == null || response.isEmpty() )
				return true; // conteudo response nao veio do server ou eh vazio

			if ( !response.contains( alias.key ) )
				return true; // propriedade inexistente. verifique o alias ou a string cURL
			
			return false;
		}
		
		private String get(String response, ResponseAttribute alias) {
			if ( response.equals("fail") )
				return response;
			
			String unfortunately = "invalid_content";
			if ( isInvalid(response, alias) )
				return unfortunately;
			
			String[] puzzle = response.split( alias.key );
			if ( puzzle.length != 2 )
				return unfortunately;
			
			return puzzle[1];
		}
		
		private String getAccessToken(String response) {
			if ( !response.contains("access_token=") )
				throw new ServerUnknownResourceException();
			
			Map<String, String> entries = new HashMap<String, String>(); 
			String[] params = response.split("&");
			String equal = "=";
			for (String entry : params) {
				if ( entry == null || entry.isEmpty() )
					continue; 
				
				String[] pair = entry.split( equal );
				entries.put(pair[0] + equal, pair[1]);
			}
			
			return entries.get( fb_access_token.key );
		}
		
		private JSONObject getJSONObject(String content) {
			JSONObject jsonObj = null;
			try {
				jsonObj = new JSONObject(content);
			} catch (JSONException e) {
				throw new ServerUnavailableResourceException();
			} 
			return jsonObj;
		}
		
	}
	
	public void setResolver(FacebookResolver resolver) {
		this.resolver = resolver;
	}

}