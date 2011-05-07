package br.eng.mosaic.pigeon.common.dto;

public class UserSocialInfo extends UserInfo {
	
	public String social_id;
	public String oauth_token;
	
	public UserSocialInfo(String name, String email) {
		super(name, email);
	}
	
	public UserSocialInfo(String name, String email, String oauth_token) {
		super(name, email);
		this.oauth_token = oauth_token;
	}	

}