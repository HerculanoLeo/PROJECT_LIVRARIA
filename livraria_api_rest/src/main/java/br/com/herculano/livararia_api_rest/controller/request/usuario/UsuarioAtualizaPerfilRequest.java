package br.com.herculano.livararia_api_rest.controller.request.usuario;

import lombok.Data;

@Data
public class UsuarioAtualizaPerfilRequest {

	private Integer idUsuario;

	private Integer idPerfil;

}
