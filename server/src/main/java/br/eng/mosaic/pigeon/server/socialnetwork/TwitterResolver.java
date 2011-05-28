package br.eng.mosaic.pigeon.server.socialnetwork;

import br.eng.mosaic.pigeon.infra.exception.NotImplemetedYetException;

public class TwitterResolver extends SocialNetworkResolver {

	@Override public String getCredentials() {
		throw new NotImplemetedYetException();
	}

}