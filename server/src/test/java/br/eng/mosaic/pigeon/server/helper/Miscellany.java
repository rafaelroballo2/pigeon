package br.eng.mosaic.pigeon.server.helper;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

public class Miscellany {
	
	private static Random random = new Random();

	private static String[] getNames() {
		return new String[] { "ninja.jiraya", 
				"lion.man", "erguloides", "jaspion", "pepe.legal" };
	}
	
	public static String getName() {
		int index = random.nextInt( getNames().length );
		return getNames()[index];
	}
	
	public static String getMail() {
		return getName() + "@domain.com";
	}
	
	public static JSONObject getJSONSample() throws JSONException {
		String confuseJSONContent = "{ 'test' : '1234', 'value' : 'whatever' }";
		return new JSONObject( confuseJSONContent );
	}
	
	public static JSONObject getJSONUser() throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("id"	, getNumber() );
		obj.put("name"	, getName() );
		obj.put("email"	, getMail() );
		return obj;
	}
	
	public static String getNumber() {
		return "" + random.nextInt( Integer.MAX_VALUE );
	}

}