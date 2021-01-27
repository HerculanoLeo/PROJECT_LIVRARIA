package br.com.herculano.livararia_api_rest.controller;

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

import br.com.herculano.livararia_api_rest.controller.request.TrocaSenhaComCodigoRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioClienteCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioClienteUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioRootCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioTrocaSenhaRequest;
import br.com.herculano.livararia_api_rest.controller.request.UsuarioRootUpdateRequest;
import br.com.herculano.livararia_api_rest.controller.request.ValidaCodigoRequest;
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
		
		if(entity instanceof UsuarioCliente) {
			return ResponseEntity.ok(new UsuarioClienteResponse((UsuarioCliente) entity));
		} else {
			return ResponseEntity.ok(new UsuarioResponse(entity));
		}
	}

	@PostMapping
	public ResponseEntity<UsuarioClienteResponse> cadastraUsuario(@RequestBody @Validated UsuarioClienteCadastroRequest request,
			HttpServletResponse response) {
		UsuarioCliente entity = service.cadastraCliente(request);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioClienteResponse(entity));
	}

	@PostMapping("/root")
	public ResponseEntity<?> cadastraUsuarioRoot(@RequestBody @Validated UsuarioRootCadastroRequest request,
			HttpServletResponse response) {
		Usuario entity = service.cadastraRoot(request);

		publisher.publishEvent(new CreatedEvent(entity, response, entity.getId().toString()));

		return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioResponse(entity));
	}

	@PutMapping()
	public ResponseEntity<?> atualizaCleinte(@RequestBody UsuarioClienteUpdateRequest request) {
		UsuarioCliente entity = service.atualizaCliente(request);
		
		return ResponseEntity.status(HttpStatus.OK).body(new UsuarioClienteResponse(entity));
	}
	
	@PutMapping("/root")
	public ResponseEntity<?> atualizaRoot(@RequestBody UsuarioRootUpdateRequest request) {
		Usuario entity = service.atualizaRoot(request);

		return ResponseEntity.status(HttpStatus.OK).body(new UsuarioResponse(entity));
	}

	@PostMapping("/trocaSenha")
	public ResponseEntity<?> trocaSenha(@RequestBody UsuarioTrocaSenhaRequest request) {
		trocaSenhaService.trocaSenha(request);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/trocaSenha/validaCodigo")
	public ResponseEntity<TrocaSenhaResponse> trocaSenha(@RequestBody ValidaCodigoRequest request) {
		TrocaSenha entity = trocaSenhaService.validaCodigo(request);

		return ResponseEntity.ok(new TrocaSenhaResponse(entity));
	}

	@PostMapping("/trocaSenha/codigo")
	public ResponseEntity<?> trocaSenhaComCodigo(@RequestBody TrocaSenhaComCodigoRequest request) {
		trocaSenhaService.trocaSenhaComCodigo(request);

		return ResponseEntity.ok().build();
	}

}
