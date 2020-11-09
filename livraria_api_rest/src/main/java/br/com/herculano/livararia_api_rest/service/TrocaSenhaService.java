package br.com.herculano.livararia_api_rest.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.constants.system_message.MessageTemplate;
import br.com.herculano.livararia_api_rest.constants.system_message.TrocaSenhaMessage;
import br.com.herculano.livararia_api_rest.controller.request.TrocaSenhaComCodigoRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioTrocaSenhaRequest;
import br.com.herculano.livararia_api_rest.controller.request.ValidaCodigoRequest;
import br.com.herculano.livararia_api_rest.entity.TrocaSenha;
import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.livararia_api_rest.exception.custom.ConfirmPasswordException;
import br.com.herculano.livararia_api_rest.exception.custom.TrocaSenhaException;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.TrocaSenhaRepository;

@Service
public class TrocaSenhaService extends ServiceTemplate<TrocaSenha, TrocaSenhaRepository, TrocaSenhaMessage> {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	public TrocaSenhaService(TrocaSenhaRepository repository, @Qualifier("TrocaSenhaMessage") TrocaSenhaMessage message) {
		super(repository, message);
	}

	public void trocaSenha(UsuarioTrocaSenhaRequest request) {
		Usuario entity = usuarioService.consultaPorEmail(request.getEmail());

		if (request.getNovaSenha() != null) {

			if (request.getSenhaAntiga().equals(request.getConfirmaSenha())) {

				trocaSenhaAntiga(entity, request);

			} else {

				throw new ConfirmPasswordException(MessageTemplate.getCodigo(getMessage().getPasswordNotMatch(), null));
			}

		} else if (request.getSenhaAntiga() == null || request.getConfirmaSenha() == null) {

			geraCodido(entity);

		} else {

			throw new ConfirmPasswordException(MessageTemplate.getCodigo(getMessage().getPasswordEmpty(), null));

		}

	}

	public TrocaSenha validaCodigo(ValidaCodigoRequest request) {
		Usuario usuario = usuarioService.consultaPorEmail(request.getEmail());

		TrocaSenha entity = validaCodigo(request.getCode(), usuario.getEmail());

		return entity;
	}

	public void trocaSenhaComCodigo(TrocaSenhaComCodigoRequest request) {
		Usuario entity = usuarioService.consultaPorEmail(request.getEmail());

		if (request.getNovaSenha().equals(request.getConfirmaSenha())) {
			trocaSenhaComCodigo(request, entity);
		} else {
			throw new ConfirmPasswordException(MessageTemplate.getCodigo(getMessage().getPasswordNotMatch(), null));
		}
	}

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

		if (entities.isEmpty()) {
			throw new TrocaSenhaException(MessageTemplate.getCodigo(getMessage().getCodeNotValid(), null));
		} else {
			TrocaSenha entity = entities.get(0);

			if (LocalDateTime.now().isAfter(entity.getDataValidade())) {
				invalidaCodigosAnteriores(entity.getEmail());

				throw new TrocaSenhaException(MessageTemplate.getCodigo(getMessage().getCodeExpired(), null));
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
		if (entity.getEncoder().matches(request.getSenhaAntiga(), entity.getPassword())) {
			entity.setSenha(entity.getEncoder().encode(request.getNovaSenha()));

			usuarioService.save(entity);

			enviaConfirmacaoTrocaSenhaEmail();
		} else {
			throw new ConfirmPasswordException(MessageTemplate.getCodigo(getMessage().getWrongPassword(), null));
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
