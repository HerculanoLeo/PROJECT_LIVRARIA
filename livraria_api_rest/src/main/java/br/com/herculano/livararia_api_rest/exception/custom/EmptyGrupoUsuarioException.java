package br.com.herculano.livararia_api_rest.exception.custom;

public class EmptyGrupoUsuarioException extends RuntimeException {

	private static final long serialVersionUID = -1556512321131989010L;

	public EmptyGrupoUsuarioException(String message) {
		super(message);
	}
}
