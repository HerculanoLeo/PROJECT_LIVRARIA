package br.com.herculano.livararia_api_rest.controller.response;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import br.com.herculano.livararia_api_rest.entity.Usuario;

public class UsuarioResponse {
	
	private Integer id;
	
	private String nome;
	
	private String email;
	
	private String tipo;
	
	private BibliotecaAutenticaResponse biblioteca;
	
	private Collection<? extends GrantedAuthority> perfil;

	public UsuarioResponse(Usuario entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.email = entity.getEmail();
		this.perfil = entity.getAuthorities();
		this.tipo = entity.getTipo();
		
		if(null != entity.getBiblioteca()) {
			this.biblioteca = new BibliotecaAutenticaResponse(entity.getBiblioteca());
		}

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
	
	public String getTipo() {
		return tipo;
	}
	
	public BibliotecaAutenticaResponse getBiblioteca() {
		return biblioteca;
	}

	public Collection<? extends GrantedAuthority> getPerfil() {
		return perfil;
	}
}
