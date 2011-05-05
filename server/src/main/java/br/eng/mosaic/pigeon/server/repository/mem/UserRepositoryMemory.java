package br.eng.mosaic.pigeon.server.repository.mem;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import br.eng.mosaic.pigeon.common.domain.User;
import br.eng.mosaic.pigeon.server.repository.UserRepository;

//TODO temp soluction ad-hoc : wait dao team
public class UserRepositoryMemory implements UserRepository {

	private Map<String, User> map = new HashMap<String, User>();
	
	@Override public User getAccount(String email) {
		return map.get(email);
	}

	@Override public void update(User user) {
		Set<Entry<String,User>> entries = map.entrySet();
		
		for (Entry<String,User> entry : entries) {
			if ( entry.getValue().equals(user) ) {
				map.put( entry.getKey() , user);
			}
		}
	}	

}