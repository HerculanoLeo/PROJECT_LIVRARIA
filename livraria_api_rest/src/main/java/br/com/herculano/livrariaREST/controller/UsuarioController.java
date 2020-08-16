package br.com.herculano.livrariaREST.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.herculano.livrariaREST.controller.request.TrocaSenhaComCodigoRequest;
import br.com.herculano.livrariaREST.controller.request.UsuarioRequest;
import br.com.herculano.livrariaREST.controller.request.UsuarioTrocaSenhaRequest;
import br.com.herculano.livrariaREST.controller.request.UsuarioUpdateRequest;
import br.com.herculano.livrariaREST.controller.request.ValidaCodigoRequest;
import br.com.herculano.livrariaREST.controller.response.TrocaSenhaResponse;
import br.com.herculano.livrariaREST.controller.response.UsuarioResponse;
import br.com.herculano.livrariaREST.entity.GrupoUsuario;
import br.com.herculano.livrariaREST.entity.TrocaSenha;
import br.com.herculano.livrariaREST.entity.Usuario;
import br.com.herculano.livrariaREST.exception.custom.ConfirmPasswordException;
import br.com.herculano.livrariaREST.exception.custom.EmptyGrupoUsuarioException;
import br.com.herculano.livrariaREST.service.GrupoUsuarioService;
import br.com.herculano.livrariaREST.service.PermissaoService;
import br.com.herculano.livrariaREST.service.TrocaSenhaService;
import br.com.herculano.livrariaREST.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@Autowired
	private PermissaoService permissaoService;

	@Autowired
	private GrupoUsuarioService grupoUsuarioService;
	
	@Autowired
	private TrocaSenhaService trocaSenhaService;

	@GetMapping
	@PreAuthorize("hasAnyRole('CONSULTA_USUARIO')")
	public ResponseEntity<Page<UsuarioResponse>> consultaUsuarios(Pageable page) {
		Page<Usuario> entities = service.consulta(page);

		ResponseEntity<Page<UsuarioResponse>> response = ResponseEntity.ok(entities.map(usuario -> {
			usuario.setPermissoes(permissaoService.consultaPorIdUsuario(usuario.getId()));

			UsuarioResponse entity = new UsuarioResponse(usuario);

			return entity;
		}));

		return response;
	}

	@GetMapping("/{idUsuario}")
	@PreAuthorize("hasAnyRole('CONSULTA_USUARIO_POR_ID')")
	public ResponseEntity<UsuarioResponse> consultaPorIdUsuario(@PathVariable Integer idUsuario) {
		Optional<Usuario> optional = service.consultaPorId(idUsuario);

		if (optional.isPresent()) {
			Usuario usuario = optional.get();

			usuario.setPermissoes(permissaoService.consultaPorIdUsuario(usuario.getId()));

			UsuarioResponse entity = new UsuarioResponse(usuario);

			return ResponseEntity.ok(entity);
		} else {
			throw new EntityNotFoundException("idUsario: " + idUsuario + " not exist.");
		}
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('CADASTRAR_USUARIO')")
	public ResponseEntity<?> cadastraUsuario(@RequestBody @Validated UsuarioRequest request, UriComponentsBuilder uriBuilder) {

		if (request.getSenha().equals(request.getConfirmeSenha())) {

			Usuario entity = new Usuario(request);

			service.save(entity);

			URI uri = uriBuilder.path("/{id}").buildAndExpand(entity.getId()).toUri();

			return ResponseEntity.created(uri).build();

		} else {
			throw new ConfirmPasswordException("Confirm Password does not match Password");
		}
	}

	@PutMapping("/{idUsuario}")
	@PreAuthorize("hasAnyRole('ATUALIZAR_USUARIO')")
	public ResponseEntity<?> atualizaUsuario(@RequestBody UsuarioUpdateRequest request, @PathVariable Integer idUsuario ,UriComponentsBuilder uriBuilder ) {
		
		Optional<Usuario> optional = service.consultaPorId(idUsuario);
		
		if (optional.isPresent()) { 
			
			Usuario entity = optional.get();
			
			entity.setNome(request.getNome());
			
			service.save(entity);
			
			URI uri = uriBuilder.path("/{id}").buildAndExpand(entity.getId()).toUri();

			return ResponseEntity.created(uri).build();
		} else {
			throw new EntityNotFoundException("idUsario: " + idUsuario + " not exist.");
		}
	}

	@PutMapping("/{idUsuario}/grupo")
	@PreAuthorize("hasAnyRole('CADASTRAR_USUARIO_GRUPOS')")
	public ResponseEntity<?> adicionaGrupoUsuario(@RequestBody List<Integer> idsGrupoUsuario, @PathVariable Integer idUsuario, UriComponentsBuilder uriBuilder) {
	
		if (idsGrupoUsuario.isEmpty()) {
			throw new EmptyGrupoUsuarioException("Empty Grupo Usuario not valid.");
		}

		Optional<Usuario> optionalUsuario = service.consultaPorId(idUsuario);

		if (optionalUsuario.isPresent()) {

			List<GrupoUsuario> gruposUsuario = new ArrayList<GrupoUsuario>();

			for (Integer idGrupoUsuario : idsGrupoUsuario) {

				Optional<GrupoUsuario> grupoUsuario = grupoUsuarioService.consultaPorId(idGrupoUsuario);

				if (grupoUsuario.isPresent()) {
					gruposUsuario.add(grupoUsuario.get());
				} else {
					throw new EntityNotFoundException("idGrupoUsuario: " + idGrupoUsuario + " not exist.");
				}
			}

			Usuario entity = optionalUsuario.get();

			entity.setGrupoUsuario(gruposUsuario);

			service.save(entity);

			URI uri = uriBuilder.path("/{id}").buildAndExpand(entity.getId()).toUri();

			return ResponseEntity.created(uri).build();
		} else {
			throw new EntityNotFoundException("idUsario: " + idUsuario + " not exist.");
		}
	}
	
	@PostMapping("/trocaSenha")
	public ResponseEntity<?> trocaSenha(@RequestBody UsuarioTrocaSenhaRequest request, UriComponentsBuilder uirBuilder) {

		Optional<Usuario> optionalUsuario = service.consultaPorEmail(request.getEmail());
		
		if(!optionalUsuario.isPresent()) {
			throw new EntityNotFoundException("Email: " + request.getEmail() + " not exist.");
		}
		
		if(request.getNovaSenha() != null){
			if (request.getSenhaAntiga().equals(request.getConfirmaSenha())) {
				trocaSenhaService.trocaSenhaAntiga(optionalUsuario.get(), request);
			} else {
				throw new ConfirmPasswordException("Confirm Password does not match Password");
			}
		} else if (request.getSenhaAntiga() == null || request.getConfirmaSenha() == null) {
			trocaSenhaService.geraCodido(optionalUsuario.get());
		} else {
			throw new ConfirmPasswordException("Empty new password not valid");
		} 
		
		return ResponseEntity.ok().build();
	}
	
	
	@PostMapping("/trocaSenha/validaCodigo")
	public ResponseEntity<TrocaSenhaResponse> trocaSenha(@RequestBody ValidaCodigoRequest request, UriComponentsBuilder uirBuilder) {

		Optional<Usuario> optionalUsuario = service.consultaPorEmail(request.getEmail());
		
		if(!optionalUsuario.isPresent()) {
			throw new EntityNotFoundException("Email: " + request.getEmail() + " not exist.");
		}
		
		TrocaSenha trocaSenha = trocaSenhaService.validaCodigo(request.getCodigo(), request.getEmail());
		
		return ResponseEntity.ok(new TrocaSenhaResponse(trocaSenha));
	}
	
	
	@PostMapping("/trocaSenha/codigo")
	public ResponseEntity<?> trocaSenhaComCodigo(@RequestBody TrocaSenhaComCodigoRequest request, UriComponentsBuilder uirBuilder) {
		
		Optional<Usuario> optionalUsuario = service.consultaPorEmail(request.getEmail());
		
		if(!optionalUsuario.isPresent()) {
			throw new EntityNotFoundException("Email: " + request.getEmail() + " not exist.");
		}
		
		if (request.getNovaSenha().equals(request.getConfirmaSenha())) {
			trocaSenhaService.trocaSenhaComCodigo(request, optionalUsuario.get());
		}else {
			throw new ConfirmPasswordException("Confirm Password does not match Password");
		}
		
		return ResponseEntity.ok().build();
	}
	
}
