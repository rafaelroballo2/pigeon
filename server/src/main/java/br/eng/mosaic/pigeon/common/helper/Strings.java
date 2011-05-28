package br.eng.mosaic.pigeon.common.helper;

public class Strings {
	
	public static String replace( String string, char[] oldChars, char newChar ) {
		for (char xar : oldChars)
			string = string.replace(xar, newChar);	
		return string;
	}
	
}