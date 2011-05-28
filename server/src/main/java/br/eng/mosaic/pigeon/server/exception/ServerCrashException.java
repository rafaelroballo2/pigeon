package br.eng.mosaic.pigeon.server.exception;

public class ServerCrashException extends RuntimeException {

	private static final long serialVersionUID = 5065443852530113766L;

	public ServerCrashException() {
		super("server fora do ar, tente novamente mais tarde ou contate administrador");
	}

}