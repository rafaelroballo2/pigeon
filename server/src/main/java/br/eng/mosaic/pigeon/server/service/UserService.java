package br.eng.mosaic.pigeon.server.service;

import br.eng.mosaic.pigeon.common.domain.User;
import br.eng.mosaic.pigeon.common.dto.UserInfo;
import br.eng.mosaic.pigeon.server.repository.UserRepository;

public class UserService {
	
	private UserRepository userRepository;
	
	public User connect(UserInfo userInfo) {
		
		User user = userRepository.getAccount( userInfo.email );
		if ( user == null ) {
			// TODO next spring : register and confirm registration
		}
		
		// TODO next sprint : update statisticals of user 
		
		userRepository.update( user );
		
		return user;
	}

}