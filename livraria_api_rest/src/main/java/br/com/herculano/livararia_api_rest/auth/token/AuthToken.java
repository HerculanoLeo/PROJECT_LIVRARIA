package br.com.herculano.livararia_api_rest.auth.token;

import java.util.Date;

import br.com.herculano.livararia_api_rest.controller.response.UsuarioResponse;
import lombok.Data;

@Data
public class AuthToken {

	private String tipo = "bearer ";
	private String token;
	private Date expireToken;
	private UsuarioResponse usuario;

	public AuthToken(String token, Date expireToken, UsuarioResponse usuarioDTO) {
		this.token = token;
		this.expireToken = expireToken;
		this.usuario = usuarioDTO;
	}

}
