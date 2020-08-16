package br.com.herculano.livrariaREST.auth.token;

import java.util.Date;

import br.com.herculano.livrariaREST.controller.response.UsuarioResponse;
import lombok.Data;

@Data
public class AuthToken {

	private String tipo = "Bearer ";
	private String token;
	private Date expireToken;
	private UsuarioResponse usuario;

	public AuthToken(String token, Date expireToken, UsuarioResponse usuarioDTO) {
		this.token = token;
		this.expireToken = expireToken;
		this.usuario = usuarioDTO;
	}

}
