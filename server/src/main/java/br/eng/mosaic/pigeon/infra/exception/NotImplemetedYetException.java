package br.eng.mosaic.pigeon.infra.exception;

public class NotImplemetedYetException extends RuntimeException {

	private static final long serialVersionUID = 5065443852530113766L;

	public NotImplemetedYetException() {
		super("ainda não implementado pelo desenvolvedor");
	}

}