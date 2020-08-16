package br.com.herculano.livararia_api_rest.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.herculano.livararia_api_rest.controller.request.UsuarioRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_usuario")
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 2138398761017176557L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_USUARIO")
	@SequenceGenerator(name = "SQ_USUARIO", sequenceName = "SQ_USUARIO", allocationSize = 1)
	private Integer id;
	
	@Column(name = "email", unique = true, length = 200)
	private String email;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "senha")
	private String senha;
	
	@ManyToMany
	@JoinTable(name = "tb_usuario_grupo_usuario", joinColumns =
				@JoinColumn (name = "id_usuario", referencedColumnName = "id"), inverseJoinColumns = 
						@JoinColumn (name = "id_grupo_usuario", referencedColumnName = "id"))
	private List<GrupoUsuario> grupoUsuario;
	
	public Usuario(UsuarioRequest request) {
		this.nome = request.getNome();
		this.email = request.getEmail();
		this.senha = this.encoder.encode(request.getSenha());
	}
	
	
	@Transient
	private List<Permissao> permissoes;
	
	@Transient
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.permissoes;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
