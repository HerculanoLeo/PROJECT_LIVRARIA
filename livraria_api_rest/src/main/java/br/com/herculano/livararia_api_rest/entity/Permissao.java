package br.com.herculano.livararia_api_rest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.herculano.livararia_api_rest.constants.TiposUsuariosEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_permissao")
@Builder
public class Permissao implements GrantedAuthority {

	private static final long serialVersionUID = -3568360990613045980L;

	@Id 
	@Column(name = "codigo", length = 50) 
	@JsonIgnore
	private String codigo;
	
	@Column(name = "tipo", nullable = false)
	private String tipo;
	
	public Permissao(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String getAuthority() {
		return this.codigo;
	}
	
	@JsonIgnore
	public boolean isROOT() {
		return tipo.equals(TiposUsuariosEnum.ROOT.getValor()) || isBiblioteca() || isCliente();
	}

	@JsonIgnore
	public boolean isBiblioteca() {
		return tipo.equals(TiposUsuariosEnum.ADMINISTRADOR.getValor()) || tipo.equals(TiposUsuariosEnum.OPERADOR.getValor()) || isCliente();
	}

	@JsonIgnore
	public boolean isCliente() {
		return tipo.equals(TiposUsuariosEnum.CLIENTE.getValor());
	}

	@JsonIgnore
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Permissao other = (Permissao) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@JsonIgnore
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	
	
}
