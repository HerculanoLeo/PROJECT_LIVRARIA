package br.com.herculano.livararia_api_rest.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioAtualizaPerfilRequest;
import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioClienteCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioClienteUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioRootCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioRootUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioTrocaSenhaComCodigoRequest;
import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioTrocaSenhaRequest;
import br.com.herculano.livararia_api_rest.controller.request.usuario.UsuarioValidaCodigoRequest;
import br.com.herculano.livararia_api_rest.controller.response.TrocaSenhaResponse;
import br.com.herculano.livararia_api_rest.controller.response.UsuarioClienteResponse;
import br.com.herculano.livararia_api_rest.controller.response.UsuarioResponse;
import br.com.herculano.livararia_api_rest.entity.TrocaSenha;
import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.livararia_api_rest.entity.UsuarioCliente;
import br.com.herculano.livararia_api_rest.event.CreatedEvent;
import br.com.herculano.livararia_api_rest.service.TrocaSenhaService;
import br.com.herculano.livararia_api_rest.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@Autowired
	private TrocaSenhaService trocaSenhaService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public ResponseEntity<Page<UsuarioResponse>> consultaUsuarios(UsuarioConsultaRequest entityRequest, Pageable page) {
		Page<Usuario> entities = service.consultaPorFiltro(entityRequest, page);

		ResponseEntity<Page<UsuarioResponse>> response = ResponseEntity.ok(entities.map(UsuarioResponse::new));

		return response;
	}

	@GetMapping("/{idUsuario}")
	public ResponseEntity<?> consultaPorIdUsuario(@PathVariable Integer idUsuario) {
		Usuario entity = service.consultaPorId(idUsuario);

		if (entity.isCliente()) {
			return ResponseEntity.ok(new UsuarioClienteResponse((UsuarioCliente) entity));
		}

		return ResponseEntity.ok(new UsuarioResponse(entity));
	}

	@PostMapping
	public ResponseEntity<UsuarioClienteResponse> cadastraUsuario(@RequestBody @Validated UsuarioClienteCadastroRequest request, HttpServletResponse response) {
		UsuarioCliente entity = service.cadastraCliente(request);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioClienteResponse(entity));
	}

	@PostMapping("/root")
	public ResponseEntity<?> cadastraUsuarioRoot(@RequestBody @Validated UsuarioRootCadastroRequest request, HttpServletResponse response) {
		Usuario entity = service.cadastraRoot(request);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioResponse(entity));
	}

	@PutMapping("/{idUsuario}")
	public ResponseEntity<?> atualizaCleinte(@PathVariable("idUsuario") Integer idUsuario, @RequestBody @Validated UsuarioClienteUpdateRequest request) {
		UsuarioCliente entity = service.atualizaCliente(idUsuario, request);

		return ResponseEntity.status(HttpStatus.OK).body(new UsuarioClienteResponse(entity));
	}

	@PutMapping("/root/{idUsuario}")
	public ResponseEntity<?> atualizaRoot(@PathVariable("idUsuario") Integer idUsuario, @RequestBody @Validated UsuarioRootUpdateRequest request) {
		Usuario entity = service.atualizaRoot(idUsuario, request);

		return ResponseEntity.status(HttpStatus.OK).body(new UsuarioResponse(entity));
	}

	@PutMapping("/perfil")
	@PreAuthorize("@resourcesSecurity.isAtualizaPerfilUsuario(authentication, #entityRequest)")
	public ResponseEntity<?> atualizaPerfil(@RequestBody UsuarioAtualizaPerfilRequest entityRequest) {
		service.atualizaPerfil(entityRequest);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/troca_senha")
	public ResponseEntity<?> trocaSenha(@RequestBody UsuarioTrocaSenhaRequest request) {
		trocaSenhaService.trocaSenha(request);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/troca_senha/valida_codigo")
	public ResponseEntity<TrocaSenhaResponse> trocaSenha(@RequestBody UsuarioValidaCodigoRequest request) {
		TrocaSenha entity = trocaSenhaService.validaCodigo(request);

		return ResponseEntity.ok(new TrocaSenhaResponse(entity));
	}

	@PostMapping("/troca_senha/codigo")
	public ResponseEntity<?> trocaSenhaComCodigo(@RequestBody UsuarioTrocaSenhaComCodigoRequest request) {
		trocaSenhaService.trocaSenhaComCodigo(request);

		return ResponseEntity.ok().build();
	}

}
