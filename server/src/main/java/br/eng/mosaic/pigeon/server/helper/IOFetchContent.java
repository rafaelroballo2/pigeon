package br.eng.mosaic.pigeon.server.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.json.JSONException;
import org.json.JSONObject;

public class IOFetchContent {
	
	private static final Logger logger = 
		Logger.getLogger(IOFetchContent.class.getName());

	public String getContent(String theUrl) {
		
		String content;
		try {
			logger.log(Level.INFO, "get :> " + theUrl);
			URL url = new URL(theUrl);
			BufferedReader bufferedReader = getReader( url );
			content = extract( bufferedReader );
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return content;
	}
	
	public byte[] getStream( URI uri ) throws URISyntaxException, ClientProtocolException, IOException {
		HttpEntity entity = getEntityFromResponse( uri );

		InputStream stream = entity.getContent();
		int length = (int) entity.getContentLength();
		
		return readFully( stream, length );
	}
	
	public JSONObject convertJSONObject( String content ) {
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(content);
		} catch (JSONException e) {
			throw new RuntimeException("erro ao extrair objeto json");
		} 
		return jsonObj;
	}
	
	private byte[] readFully(InputStream stream, int length) throws IOException {
		byte[] bytes = new byte[length];
		int totalRead = 0;
		int lastRead = -1;
		do {
			lastRead = stream.read(bytes, totalRead, length);
			totalRead += lastRead;
		} while (lastRead > 0 && totalRead < length);
		return bytes;
	}

	// TODO refatorar para otimizar posteriormente e remover depreciação > DefaultRedirectHandler
	private HttpEntity getEntityFromResponse(URI uri) 
			throws URISyntaxException, ClientProtocolException, IOException{
		
		HttpGet get = new HttpGet(uri);
	
		HttpClient client = new DefaultHttpClient();
		((DefaultHttpClient) client).setRedirectHandler(new DefaultRedirectHandler());
		
		HttpResponse response = client.execute(get);
		return response.getEntity();
	}
	
	private BufferedReader getReader( URL url ) throws IOException {
		URLConnection urlConnection = url.openConnection();
		InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream()); 
		return new BufferedReader( reader );
	}
	
	private String extract(BufferedReader reader) throws IOException {
		StringBuilder content = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null)
			content.append(line);
		reader.close();
		return content.toString();
	}
	
}