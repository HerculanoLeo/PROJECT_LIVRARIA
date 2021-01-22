package br.com.herculano.livararia_api_rest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_configuracao")
public class Configuracao {

	@Id
	@Column(name = "codigo")
	private String codigo;
	
	@Column(name = "valor")
	private String valor;
	
}
