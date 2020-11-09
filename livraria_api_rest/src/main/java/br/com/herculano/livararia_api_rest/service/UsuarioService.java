package br.com.herculano.livararia_api_rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.constants.system_message.MessageTemplate;
import br.com.herculano.livararia_api_rest.constants.system_message.UsuarioMessage;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioUpdateRequest;
import br.com.herculano.livararia_api_rest.entity.GrupoUsuario;
import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.livararia_api_rest.exception.custom.ConfirmPasswordException;
import br.com.herculano.livararia_api_rest.exception.custom.EmptyGrupoUsuarioException;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.UsuarioRepository;

@Service
public class UsuarioService extends ServiceTemplate<Usuario, UsuarioRepository, UsuarioMessage> {

	@Autowired
	private GrupoUsuarioService grupoUsuarioService;

	@Autowired
	public UsuarioService(UsuarioRepository repository, @Qualifier("UsuarioMessage") UsuarioMessage message) {
		super(repository, message);
	}

	public Usuario cadastra(UsuarioCadastroRequest request) {
		if (request.getSenha().equals(request.getConfirmeSenha())) {
			Usuario entity = new Usuario(request);

			super.save(entity);

			return entity;
		} else {
			throw new ConfirmPasswordException(MessageTemplate.getCodigo(getMessage().getPasswordNotMatch(), null));
		}
	}

	public Usuario atualiza(Integer id, UsuarioUpdateRequest request) {
		Usuario entity = super.consultaPorId(id);

		entity.setNome(request.getNome());

		super.save(entity);

		return entity;
	}

	public Usuario consultaPorEmail(String email) {
		Optional<Usuario> optionalUsuario = getRepository().findByEmail(email);

		if (!optionalUsuario.isPresent()) {
			Object[] args = {email};
			
			throw new EntityNotFoundException(MessageTemplate.getCodigo(getMessage().getEmailNotFound(), args));
		}

		return optionalUsuario.get();
	}

	public Usuario cadastraGrupoUsuarioPorIds(Integer idUsuario, List<Integer> idsGrupoUsuario) {

		if (idsGrupoUsuario.isEmpty()) {
			throw new EmptyGrupoUsuarioException(MessageTemplate.getCodigo(getMessage().getEmptyGroupUser(), null));
		}

		Usuario entity = super.consultaPorId(idUsuario);

		List<GrupoUsuario> gruposUsuario = new ArrayList<GrupoUsuario>();

		for (Integer idGrupoUsuario : idsGrupoUsuario) {

			GrupoUsuario grupoUsuario = grupoUsuarioService.consultaPorId(idGrupoUsuario);

			gruposUsuario.add(grupoUsuario);
		}

		entity.setGrupoUsuario(gruposUsuario);

		super.save(entity);

		return entity;
	}

	public Page<Usuario> consultaOperadoresPorIdBiblioteca(Integer idBiblioteca, Pageable page) {
		return getRepository().consultaOperadoresPorIdBiblioteca(idBiblioteca, page);
	}

}
