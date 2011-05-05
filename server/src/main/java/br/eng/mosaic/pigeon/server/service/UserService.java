package br.eng.mosaic.pigeon.server.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.eng.mosaic.pigeon.common.domain.User;
import br.eng.mosaic.pigeon.common.dto.UserInfo;
import br.eng.mosaic.pigeon.server.repository.UserRepository;

public class UserService {
	
	@Autowired private UserRepository userRepository;
	
	public User connect(UserInfo userInfo) {
		
		User user = userRepository.getAccount( userInfo.email );
		if ( user == null ) {
			/*
			 * TODO register and confirm registration
			 */
		}
		
		updateInfoStatisticals(user);
		userRepository.update( user );
		
		return user;
	}
	
	private void updateInfoStatisticals(User user) {}
	
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

}