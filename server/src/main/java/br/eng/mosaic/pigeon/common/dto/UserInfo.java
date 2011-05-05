package br.eng.mosaic.pigeon.common.dto;

public class UserInfo {
	
	public String idPigeon;
	public String idFacebook;
	public String idTwitter;
	
	public String email;
	public String firstName;
	public String lastName;
	
	public UserInfo(String first, String last, String email) {
		this.firstName = first;
		this.lastName = last;
		this.email = email;
	}

}