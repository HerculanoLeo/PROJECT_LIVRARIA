package br.com.herculano.livararia_api_rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.herculano.livararia_api_rest.controller.request.TrocaSenhaComCodigoRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioTrocaSenhaRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.ValidaCodigoRequest;
import br.com.herculano.livararia_api_rest.controller.response.TrocaSenhaResponse;
import br.com.herculano.livararia_api_rest.controller.response.UsuarioResponse;
import br.com.herculano.livararia_api_rest.entity.TrocaSenha;
import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.livararia_api_rest.event.CreatedEvent;
import br.com.herculano.livararia_api_rest.service.PermissaoService;
import br.com.herculano.livararia_api_rest.service.TrocaSenhaService;
import br.com.herculano.livararia_api_rest.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@Autowired
	private PermissaoService permissaoService;
	
	@Autowired
	private TrocaSenhaService trocaSenhaService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
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
	public ResponseEntity<UsuarioResponse> consultaPorIdUsuario(@PathVariable Integer idUsuario) {
		Usuario entity = service.consultaPorId(idUsuario);

		entity.setPermissoes(permissaoService.consultaPorIdUsuario(entity.getId()));

		return ResponseEntity.ok(new UsuarioResponse(entity));
	}

	@PostMapping
	public ResponseEntity<?> cadastraUsuario(@RequestBody @Validated UsuarioCadastroRequest request,
			HttpServletResponse response) {

		Usuario entity = service.cadastra(request);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioResponse(entity));
	}

	@PutMapping("/{idUsuario}")
	public ResponseEntity<?> atualizaUsuario(@RequestBody UsuarioUpdateRequest request, @PathVariable Integer idUsuario,
			HttpServletResponse response) {

		Usuario entity = service.atualiza(idUsuario, request);

		return ResponseEntity.status(HttpStatus.OK).body(new UsuarioResponse(entity));
	}

	@PutMapping("/{idUsuario}/grupo")
	public ResponseEntity<?> adicionaGrupoUsuario(@RequestBody List<Integer> idsGrupoUsuario,
			@PathVariable Integer idUsuario, HttpServletResponse response) {

		Usuario entity = service.cadastraGrupoUsuarioPorIds(idUsuario, idsGrupoUsuario);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.OK).body(entity);
	}

	@PostMapping("/trocaSenha")
	public ResponseEntity<?> trocaSenha(@RequestBody UsuarioTrocaSenhaRequest request,
			UriComponentsBuilder uirBuilder) {
		
		trocaSenhaService.trocaSenha(request);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/trocaSenha/validaCodigo")
	public ResponseEntity<TrocaSenhaResponse> trocaSenha(@RequestBody ValidaCodigoRequest request,
			HttpServletResponse response) {
		
		TrocaSenha entity = trocaSenhaService.validaCodigo(request);

		return ResponseEntity.ok(new TrocaSenhaResponse(entity));
	}

	@PostMapping("/trocaSenha/codigo")
	public ResponseEntity<?> trocaSenhaComCodigo(@RequestBody TrocaSenhaComCodigoRequest request,
			HttpServletResponse response) {

		trocaSenhaService.trocaSenhaComCodigo(request);

		return ResponseEntity.ok().build();
	}

}
