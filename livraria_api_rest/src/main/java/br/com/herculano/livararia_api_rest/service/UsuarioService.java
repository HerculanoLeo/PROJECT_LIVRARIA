package br.com.herculano.livararia_api_rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.controller.request.TrocaSenhaComCodigoRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioTrocaSenhaRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.ValidaCodigoRequest;
import br.com.herculano.livararia_api_rest.entity.GrupoUsuario;
import br.com.herculano.livararia_api_rest.entity.TrocaSenha;
import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.livararia_api_rest.exception.custom.ConfirmPasswordException;
import br.com.herculano.livararia_api_rest.exception.custom.EmptyGrupoUsuarioException;
import br.com.herculano.livararia_api_rest.repository.jpaRepository.UsuarioRepository;

@Service
public class UsuarioService extends ServiceTemplate<Usuario, UsuarioRepository> {

	@Autowired
	private GrupoUsuarioService grupoUsuarioService;

	@Autowired
	private TrocaSenhaService trocaSenhaService;

	@Autowired
	public UsuarioService(UsuarioRepository repository) {
		super(repository);
	}

	public Usuario cadastra(UsuarioRequest request) {
		if (request.getSenha().equals(request.getConfirmeSenha())) {
			Usuario entity = new Usuario(request);

			super.save(entity);

			return entity;
		} else {
			throw new ConfirmPasswordException("Confirm Password does not match Password");
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
			throw new EntityNotFoundException("Email: " + email + " not exist.");
		}

		return optionalUsuario.get();
	}

	public Usuario cadastraGrupoUsuarioPorIds(Integer idUsuario, List<Integer> idsGrupoUsuario) {

		if (idsGrupoUsuario.isEmpty()) {
			throw new EmptyGrupoUsuarioException("Empty Grupo Usuario not valid.");
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

	public void trocaSenha(UsuarioTrocaSenhaRequest request) {
		Usuario entity = consultaPorEmail(request.getEmail());

		if (request.getNovaSenha() != null) {
			
			if (request.getSenhaAntiga().equals(request.getConfirmaSenha())) {
				
				trocaSenhaService.trocaSenhaAntiga(entity, request);
				
			} else {
				
				throw new ConfirmPasswordException("Confirm Password does not match Password");
			}
			
		} else if (request.getSenhaAntiga() == null || request.getConfirmaSenha() == null) {
			
			trocaSenhaService.geraCodido(entity);
			
		} else {
			
			throw new ConfirmPasswordException("Empty new password not valid");
			
		}

	}

	public TrocaSenha validaCodigo(ValidaCodigoRequest request) {
		Usuario usuario = consultaPorEmail(request.getEmail());

		TrocaSenha entity = trocaSenhaService.validaCodigo(request.getCodigo(), usuario.getEmail());
		
		return entity;
	}

	public void trocaSenhaComCodigo(TrocaSenhaComCodigoRequest request) {
		Usuario entity = consultaPorEmail(request.getEmail());

		if (request.getNovaSenha().equals(request.getConfirmaSenha())) {
			trocaSenhaService.trocaSenhaComCodigo(request, entity);
		} else {
			throw new ConfirmPasswordException("Confirm Password does not match Password");
		}
	}
}
