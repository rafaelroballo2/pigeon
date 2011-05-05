package br.eng.mosaic.pigeon.server.socialnetwork;

import static br.eng.mosaic.pigeon.server.socialnetwork.SocialNetworkResolver.ResponseAttribute.fb_access_token;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import br.eng.mosaic.pigeon.common.dto.UserInfo;
import br.eng.mosaic.pigeon.infra.exception.NotImplemetedYetException;
import br.eng.mosaic.pigeon.server.helper.IOFetchContent;
import br.eng.mosaic.pigeon.server.socialnetwork.SocialNetworkResolver.ResponseAttribute;

public class FacebookClient {
	
	private FacebookResolver resolver;
	private IOFetchContent ioFetch;
	private Tagger tagger;
	
	public void setResolver(FacebookResolver resolver) {
		this.resolver = resolver;
	}
	
	public FacebookClient() {
		ioFetch = new IOFetchContent();
		tagger = new Tagger();
	}
	
	public String getAccessTokenFromApplication() {
		String cURL = resolver.getAccessTokenFromApplication();
		String response = ioFetch.getContent( cURL );
		return tagger.get(response, fb_access_token);
	}
	
	public String getStartConnection(String callback) {
		String cURL = resolver.getUrlStartConnection( callback );
		return "redirect:" + cURL;
	}
	
	/*
	 * TODO mudar para ao inves de > resolver.getAccessTokenFromUser(callback, hash);
	 * 		chamar resolver.configure( ACCESS_TOKEN_FROM_USER, callback, hash )
	 * 
	 * o tratamento no resolver seria pegar o array de strings e quem chamou 
	 * 		vai saber tratar e implementa-lo adequadamente
	 */
	public String getAccessTokenFromUser(String callback, String hash) {
		String cURL = resolver.getAccessTokenFromUser(callback, hash); 
		String response = ioFetch.getContent( cURL );
		return tagger.getAccessToken( response );
	}
	
	// TODO refatorar para usar algum parser json e melhorar coesao
	public UserInfo getBasicUserInfo(String token) {
		String cURL = resolver.getBasicUserInfo( token );
		String response = ioFetch.getContent( cURL );
		JSONObject obj = tagger.getJSONObject( response );

		UserInfo user;
		try {
			String firstName = obj.getString("name");
			String email = obj.getString("email");
			user = new UserInfo(firstName, null, email);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		
		return user;
	}
	
	public byte[] getPicture(String token) throws ClientProtocolException, URISyntaxException, IOException {
		String uri = resolver.getPictureFromUser( token );
		return ioFetch.getStream( uri );
	}
	
	public void postMessage(String message) {
		throw new NotImplemetedYetException();
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
			String unfortunately = "invalid_content";
			if ( isInvalid(response, alias) )
				return unfortunately;
			
			String[] puzzle = response.split( alias.key );
			if ( puzzle.length != 2  )
				return unfortunately;
			
			return puzzle[1];
		}
		
		private String getAccessToken(String response) {
			
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
		
		private JSONObject getJSONObject( String content ) {
			JSONObject jsonObj = null;
			try {
				jsonObj = new JSONObject(content);
			} catch (JSONException e) {
				throw new RuntimeException("erro ao extrair objeto json");
			} 
			return jsonObj;
		}
		
	}

}