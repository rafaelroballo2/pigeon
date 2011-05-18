package br.eng.mosaic.pigeon.server.exception;

public class ServerUnavailableResourceException extends RuntimeException {

	private static final long serialVersionUID = 5065443852530113766L;

	public ServerUnavailableResourceException() {
		super("recurso ou conteúdo não disponível no server. \n" +
				"verifique parâmetros enviado, disponibilidade do server ou contate administrador");
	}

}