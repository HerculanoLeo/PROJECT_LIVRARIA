package br.com.herculano.livararia_api_rest.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.herculano.livararia_api_rest.constants.TiposUsuariosEnum;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioRootCadastroRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 2138398761017176557L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_USUARIO")
	@SequenceGenerator(name = "SQ_USUARIO", sequenceName = "SQ_USUARIO", allocationSize = 1)
	private Integer id;

	@Column(name = "email", unique = true, length = 200)
	private String email;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "senha", nullable = false)
	private String senha;

	@Column(name = "tp_usuario", nullable = false)
	private String tipoUsuario;

	@Column(name = "idioma", nullable = false)
	private String idioma;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_perfil", referencedColumnName = "id", nullable = false)
	private Perfil perfil;

	public Usuario(String nome, String email, String senha, String tipoUsuario, String idioma, Perfil perfil) {
		this.nome = nome;
		this.email = email;
		this.senha = this.encoder.encode(senha);
		this.tipoUsuario = tipoUsuario;
		this.idioma = idioma;
		this.perfil = perfil;
	}

	public Usuario(UsuarioRootCadastroRequest entityRequest) {
		this.nome = entityRequest.getNome();
		this.email = entityRequest.getEmail();
		this.senha = this.encoder.encode(entityRequest.getSenha());
		this.tipoUsuario = TiposUsuariosEnum.ROOT.getValor();
		this.idioma = entityRequest.getIdioma();
		this.perfil = entityRequest.getPerfil();
	}

	@Transient
	private List<Permissao> permissoes;

	@Transient
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Transient
	private Integer idUsuarioAdministrador;
	
	@Transient
	private List<Integer> idsBiblioteca;

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

	public boolean isBiblioteca() {
		return TiposUsuariosEnum.OPERADOR.getValor().equals(tipoUsuario)
				|| TiposUsuariosEnum.ADMINISTRADOR.getValor().equals(tipoUsuario);
	}
	
	public boolean isROOT() {
		return TiposUsuariosEnum.ROOT.getValor().equals(tipoUsuario);
	}

}
