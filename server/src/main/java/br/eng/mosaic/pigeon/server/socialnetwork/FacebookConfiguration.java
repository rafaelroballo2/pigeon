package br.eng.mosaic.pigeon.server.socialnetwork;

public class FacebookConfiguration {

	public String id;
	public String keySecret;
	public String keyAPI;
	public String root;
	
	public FacebookConfiguration() {
		this.id = "150586265008036";
		this.keySecret = "346fba40bd7eb18f4c29984bf31fd18d";
		this.keyAPI = "afb21c6fd44d914ab89c717aa08c3e92";
		this.root = "http://localhost:6967/";
//		this.root = "https://graph.facebook.com/";
	}
	
}