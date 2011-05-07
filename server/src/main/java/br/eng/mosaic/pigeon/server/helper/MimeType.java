package br.eng.mosaic.pigeon.server.helper;

public enum MimeType {
	
	text_plain( "text/plain" ), text_html( "text/html" ),
	text_json( "text/json" ), text_xml( "text/xml" ), 
		binary_default( "application/octet-stream" );

	private MimeType(String type) {
		this.type = type;
	}
	
	public String type;

}