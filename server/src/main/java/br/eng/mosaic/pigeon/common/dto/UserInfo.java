package br.eng.mosaic.pigeon.common.dto;

import java.util.ArrayList;
import java.util.List;

import br.eng.mosaic.pigeon.common.domain.SocialNetwork.Social;

public class UserInfo {
	
	public String name;
	public String email;
	public List<SocialInfo> socials;
	
	public UserInfo(String name, String email) {
		this.name = name;
		this.email = email;
		this.socials = new ArrayList<SocialInfo>(); 
	}
	
	public class SocialInfo {
		public String id;
		public String token;
		public Social social;
		
		public SocialInfo(Social social, String id, String token) {
			this.social = social;
			this.id = id;
			this.token = token;
		}
	}
	
	public void add( Social social, String id, String token ) {
		socials.add( new SocialInfo(social, id, token) );
	}
	
	public SocialInfo get(Social social) {
		for (SocialInfo info : this.socials) {
			if ( info.social.equals( social ) )
				return info;
		}
		return null;
	}
	
}