package br.eng.mosaic.pigeon.server.service;

import br.eng.mosaic.pigeon.common.domain.User;
import br.eng.mosaic.pigeon.common.dto.UserInfo;
import br.eng.mosaic.pigeon.server.repository.UserRepository;

public class UserService {
	
	private UserRepository userRepository;
	
	public User connect(UserInfo userInfo) {
		
		User user = userRepository.getAccount( userInfo.email );
		if ( user == null ) {
			/*
			 * TODO register and confirm registration
			 * TODO refactor code below, argh!
			 */
			user = new User();
			user.name = userInfo.name;
			user.email = userInfo.email;
			userRepository.insert(user);
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