package br.com.herculano.livararia_api_rest.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_troca_senha")
public class TrocaSenha {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_TROCA_SENHA")
	@SequenceGenerator(name = "SQ_TROCA_SENHA", sequenceName = "SQ_TROCA_SENHA", allocationSize = 1)
	private Integer id;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "dt_solicitacao", nullable = false)
	private LocalDateTime dataSolicitada;
	
	@Column(name = "dt_validade", nullable = false)
	private LocalDateTime dataValidade;
	
	@Column(name = "code", nullable = false)
	private String code;
	
	@Column(name = "status", nullable = false)
	private String status;
	
	public TrocaSenha(Usuario usuario) {
		this.email = usuario.getEmail();
		this.nome = usuario.getNome();
	}

}