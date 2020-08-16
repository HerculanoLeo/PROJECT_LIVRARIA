package br.com.herculano.livararia_api_rest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tb_permissoes")
public class Permissao implements GrantedAuthority {

	private static final long serialVersionUID = -3568360990613045980L;

	@Id 
	@Column(name = "codigo", length = 50) 
	@JsonIgnore
	private String codigo;
	
	public Permissao(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String getAuthority() {
		return this.codigo;
	}

}
