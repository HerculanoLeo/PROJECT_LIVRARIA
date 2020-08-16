package br.com.herculano.livrariaREST.exception.custom;

public class TrocaSenhaException extends RuntimeException{

	private static final long serialVersionUID = -3783418308573908847L;
	
	public TrocaSenhaException(String message) {
		super(message);
	}

}
