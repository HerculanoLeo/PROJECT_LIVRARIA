package br.com.herculano.livararia_api_rest.exception.custom;

public class TrocaSenhaException extends RuntimeException{

	private static final long serialVersionUID = -3783418308573908847L;
	
	public TrocaSenhaException(String message) {
		super(message);
	}

}
