package br.com.herculano.livrariaREST.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.herculano.livrariaREST.controller.request.TrocaSenhaComCodigoRequest;
import br.com.herculano.livrariaREST.controller.request.UsuarioTrocaSenhaRequest;
import br.com.herculano.livrariaREST.entity.TrocaSenha;
import br.com.herculano.livrariaREST.entity.Usuario;
import br.com.herculano.livrariaREST.exception.custom.ConfirmPasswordException;
import br.com.herculano.livrariaREST.exception.custom.TrocaSenhaException;
import br.com.herculano.livrariaREST.repository.jpaRepository.TrocaSenhaRepository;

@Service
public class TrocaSenhaService extends ServiceTemplate<TrocaSenha, TrocaSenhaRepository> {

	@Autowired
	public TrocaSenhaService(TrocaSenhaRepository repository) {
		super(repository);
	}

	@Autowired
	private UsuarioService usuarioService;
	
	@Transactional(rollbackOn = Throwable.class)
	public void geraCodido(Usuario usuario) {
		TrocaSenha entity = new TrocaSenha(usuario);
		
		LocalDateTime dataSolicitada = LocalDateTime.now();
		LocalDateTime dataValidade = dataSolicitada.plusMinutes(30);
		
		entity.setDataSolicitada(dataSolicitada);
		entity.setDataValidade(dataValidade);
		
		entity.setCode(geraCodigo());
		entity.setStatus("A");
		
		invalidaCodigosAnteriores(usuario.getEmail());
		
		save(entity);
		
		enviaCodigoEmail(entity.getCode());
		
	}

	public TrocaSenha validaCodigo(String codigo, String email) {
		
		TrocaSenha filterEntity = new TrocaSenha();
		
		filterEntity.setEmail(email);
		filterEntity.setCode(codigo);
		filterEntity.setStatus("A");
		
		List<TrocaSenha> entities = getRepository().findAll(Example.of(filterEntity));
		
		if(entities.isEmpty()) {
			throw new TrocaSenhaException("Code not valid.");
		} else {
			TrocaSenha entity = entities.get(0);
			
			if(LocalDateTime.now().isAfter(entity.getDataValidade())) {
				invalidaCodigosAnteriores(entity.getEmail());
				
				throw new TrocaSenhaException("Code expired.");
			}
			
			return entity;
		}
	}

	public void trocaSenhaComCodigo(TrocaSenhaComCodigoRequest request, Usuario entity) {
		validaCodigo(request.getCodigo(), request.getEmail());
		
		entity.setSenha(entity.getEncoder().encode(request.getNovaSenha()));
		
		usuarioService.save(entity);
		
		invalidaCodigosAnteriores(request.getEmail());
	}
	
	public void trocaSenhaAntiga(Usuario entity, UsuarioTrocaSenhaRequest request) {
		if(entity.getEncoder().matches(request.getSenhaAntiga(), entity.getPassword())) {
			entity.setSenha(entity.getEncoder().encode(request.getNovaSenha()));
			
			usuarioService.save(entity);
			
			enviaConfirmacaoTrocaSenhaEmail();
		}else {
			throw new ConfirmPasswordException("Wrong password.");
		}
	}

	private void enviaConfirmacaoTrocaSenhaEmail() {
		System.out.println("Senha trocada com sucesso.");
		
	}

	private void enviaCodigoEmail(String codigo) {
		System.out.println(codigo);
	}
	
	private String geraCodigo() {
		return RandomStringUtils.random(8, false, true);
	}

	private void invalidaCodigosAnteriores(String email) {
		getRepository().invalidaCodigosAnteriores(email);
	}

}
