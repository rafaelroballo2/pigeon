package br.eng.mosaic.pigeon.server.repository;

import br.eng.mosaic.pigeon.common.domain.User;

public interface UserRepository {
	
	User getAccount( String email );
	
	void update(User user); // TODO remover e colocar generic dao

}