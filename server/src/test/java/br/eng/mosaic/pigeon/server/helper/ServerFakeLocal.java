package br.eng.mosaic.pigeon.server.helper;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import br.eng.mosaic.pigeon.server.helper.MimeType;

public class ServerFakeLocal extends NanoHTTPD {
	
	private MimeType mime;
	private String response;
	private Map<String, String> map;
	
	/*
	 * well known ports
	 * http://www.networksorcery.com/enp/protocol/ip/ports06000.htm
	 */
	private static int defaultPortNumber = 6967;

	/*
	 * specify your mime type and content to get from response
	 */
	public ServerFakeLocal(MimeType mime, Map<String, String> map, String response) throws IOException {
		super(defaultPortNumber);
		this.mime = mime;
		this.map = map;
		this.response = response;
	}
	
	/*
	 * @see br.eng.mosaic.pigeon.server.helper.NanoHTTPD#serve(java.lang.String, java.lang.String, java.util.Properties, java.util.Properties, java.util.Properties)
	 */
	protected Response serve(String uri, String method, 
			Properties header, Properties parms, Properties files) {
		
		if ( map == null || map.isEmpty() )
			return defaultResponse();
		
		System.out.println( "server.log : attending uri >" + uri.substring(1) );
		return getResponse(uri.substring(1));
	}
	
	private Response defaultResponse() {
		return new NanoHTTPD.Response(HTTP_OK, mime.type, response);
	}
	
	private Response getResponse(String uri) {
		return new NanoHTTPD.Response(HTTP_OK, mime.type, map.get(uri));
	}
	
}