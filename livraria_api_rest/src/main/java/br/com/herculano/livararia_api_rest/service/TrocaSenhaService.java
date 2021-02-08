package br.com.herculano.livararia_api_rest.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.constants.CodigoRecuperaSenhaStatusEnum;
import br.com.herculano.livararia_api_rest.constants.system_message.TrocaSenhaMessage;
import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioTrocaSenhaComCodigoRequest;
import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioTrocaSenhaRequest;
import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioValidaCodigoRequest;
import br.com.herculano.livararia_api_rest.entity.TrocaSenha;
import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.TrocaSenhaRepository;
import br.com.herculano.utilities.exceptions.ConfirmPasswordException;
import br.com.herculano.utilities.exceptions.TrocaSenhaException;
import br.com.herculano.utilities.templates.MessageTemplate;
import br.com.herculano.utilities.templates.ServiceTemplate;

@Service
public class TrocaSenhaService extends ServiceTemplate<TrocaSenha, TrocaSenhaRepository, TrocaSenhaMessage> {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	public TrocaSenhaService(TrocaSenhaRepository repository, TrocaSenhaMessage message) {
		super(repository, message);
	}

	public void trocaSenha(UsuarioTrocaSenhaRequest entityRequest) {
		Usuario entity = usuarioService.consultaPorEmail(entityRequest.getEmail());

		if (StringUtils.isNotBlank(entityRequest.getNovaSenha())) {

			if (entityRequest.getNovaSenha().equals(entityRequest.getConfirmaSenha())) {

				trocaSenhaAntiga(entity, entityRequest);

			} else {

				throw new ConfirmPasswordException(MessageTemplate.getCodigo(getMessage().getPasswordNotMatch(), null));
			}

		} else if (StringUtils.isBlank(entityRequest.getSenhaAntiga()) || StringUtils.isBlank(entityRequest.getConfirmaSenha())) {

			geraCodido(entity);

		} else {

			throw new ConfirmPasswordException(MessageTemplate.getCodigo(getMessage().getPasswordEmpty(), null));

		}
	}

	public TrocaSenha validaCodigo(UsuarioValidaCodigoRequest request) {
		Usuario usuario = usuarioService.consultaPorEmail(request.getEmail());

		TrocaSenha entity = validaCodigo(request.getCode(), usuario.getEmail());

		return entity;
	}

	public void trocaSenhaComCodigo(UsuarioTrocaSenhaComCodigoRequest entityRequest) {
		Usuario entity = usuarioService.consultaPorEmail(entityRequest.getEmail());

		if (StringUtils.isNotBlank(entityRequest.getSenha()) && StringUtils.isNotBlank(entityRequest.getConfirmaSenha())) {
			
			if (entityRequest.getSenha().equals(entityRequest.getConfirmaSenha())) {
				
				trocaSenhaComCodigo(entityRequest, entity);
			
			} else {
				
				throw new ConfirmPasswordException(MessageTemplate.getCodigo(getMessage().getPasswordNotMatch(), null));
			
			}
			
		} else {
			
			throw new ConfirmPasswordException(MessageTemplate.getCodigo(getMessage().getPasswordEmpty(), null));
		
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
		entity.setStatus(CodigoRecuperaSenhaStatusEnum.ATIVO.getValor());

		invalidaCodigosAnteriores(usuario.getEmail());

		save(entity);

		enviaCodigoEmail(entity.getCode());
	}

	public TrocaSenha validaCodigo(String codigo, String email) {

		TrocaSenha filterEntity = new TrocaSenha();

		filterEntity.setEmail(email);
		filterEntity.setCode(codigo);
		filterEntity.setStatus(CodigoRecuperaSenhaStatusEnum.ATIVO.getValor());

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

	public void trocaSenhaComCodigo(UsuarioTrocaSenhaComCodigoRequest request, Usuario entity) {
		validaCodigo(request.getCode(), request.getEmail());

		entity.setSenha(entity.getEncoder().encode(request.getSenha()));

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

	// TODO fazer notificacao de emails
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
