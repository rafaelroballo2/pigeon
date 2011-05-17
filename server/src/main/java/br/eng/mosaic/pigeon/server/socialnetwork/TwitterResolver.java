package br.eng.mosaic.pigeon.server.socialnetwork;

import br.eng.mosaic.pigeon.infra.exception.NotImplemetedYetException;

public class TwitterResolver extends SocialNetworkResolver {

	@Override public String getApplicationInfo(String fbuid, String accessToken) {
		throw new NotImplemetedYetException();
	}

	@Override public String getApplicationCredentials() {
		throw new NotImplemetedYetException();
	}

	

}