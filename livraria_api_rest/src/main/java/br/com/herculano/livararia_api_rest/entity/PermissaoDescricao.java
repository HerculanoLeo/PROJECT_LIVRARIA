package br.com.herculano.livararia_api_rest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tb_permissao_descricao") //TODO fazer a internacionalizacao do descritivo da permissao
public class PermissaoDescricao implements Serializable{

	private static final long serialVersionUID = -6146837248702794610L;

	@Id
	@ManyToOne
	@JoinColumn(name = "id_permissao", referencedColumnName = "codigo", nullable = false)
	private Permissao permissao;
	
	@Id
	@Column(name = "i18n")
	private String idioma;
	
	@Column
	private String descricao;
	
}
