package br.eng.mosaic.pigeon.server.socialnetwork;

import java.io.IOException;
import java.util.Properties;

import br.eng.mosaic.pigeon.server.helper.NanoHTTPD;

public class FacebookServerFake extends NanoHTTPD implements Runnable {
	
	private String response;
	private MimeType mime;
	
	/*
	 * well known ports
	 * http://www.networksorcery.com/enp/protocol/ip/ports06000.htm
	 */
	private static int defaultPortNumber = 6967;

	/*
	 * specify your mime type and content to get from response
	 */
	public FacebookServerFake(MimeType mime, String response) throws IOException {
		super(defaultPortNumber);
		this.mime = mime;
		this.response = response;
	}
	
	/*
	 * @see br.eng.mosaic.pigeon.server.helper.NanoHTTPD#serve(java.lang.String, java.lang.String, java.util.Properties, java.util.Properties, java.util.Properties)
	 */
	protected Response serve(String uri, String method, 
			Properties header, Properties parms, Properties files) {
		return new NanoHTTPD.Response(HTTP_OK, mime.type, response);
	}
	
	public enum MimeType {
		text_plain( "text/plain" ), text_html( "text/html" ),
			text_json( "text/json" ), text_xml( "text/xml" ), 
				binary_default( "application/octet-stream" );
		
		private MimeType(String type) {
			this.type = type;
		}
		
		private String type;
	}

	@Override public void run() {
		
	}

}