package br.com.herculano.livrariaREST.controller.response;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import br.com.herculano.livrariaREST.entity.Usuario;

public class UsuarioResponse {
	private Integer id;
	private String nome;
	private String email;
	private Collection<? extends GrantedAuthority> perfil;

	public UsuarioResponse(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.perfil = usuario.getAuthorities();
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public Collection<? extends GrantedAuthority> getPerfil() {
		return perfil;
	}

	public static List<UsuarioResponse> converter(List<Usuario> usuarios) {
		return usuarios.stream().map(UsuarioResponse::new).collect(Collectors.toList());
	}
}
