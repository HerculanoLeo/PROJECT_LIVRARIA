package br.com.herculano.livararia_api_rest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.constants.system_message.AutorMessage;
import br.com.herculano.livararia_api_rest.constants.system_message.LivroMessage;
import br.com.herculano.livararia_api_rest.controller.request.LivroCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.LivroUpdateRequest;
import br.com.herculano.livararia_api_rest.entity.Autor;
import br.com.herculano.livararia_api_rest.entity.Livro;
import br.com.herculano.livararia_api_rest.repository.jpa_repository.LivroRepository;
import br.com.herculano.utilities.exceptions.DadosInvalidosException;
import br.com.herculano.utilities.templates.MessageTemplate;
import br.com.herculano.utilities.templates.ServiceTemplate;

@Service
public class LivroService extends ServiceTemplate<Livro, LivroRepository, LivroMessage> {

	@Autowired
	private AutorService autorService;

	@Autowired
	private BibliotecaService bibliotecaService;

	@Autowired
	private AutorMessage autorMessage;

	@Autowired
	public LivroService(LivroRepository repository, LivroMessage message) {
		super(repository, message);
	}

	public Livro cadastra(LivroCadastroRequest entityRequest) {
		List<Autor> autores = new ArrayList<Autor>();

		validaAutores(entityRequest, autores);

		entityRequest.setBiblioteca(bibliotecaService.consultaPorId(entityRequest.getIdBiblioteca()));

		Livro entity = new Livro(entityRequest);

		if (!autores.isEmpty()) {
			entity.setAutores(autores);
		}

		super.save(entity);

		return entity;
	}

	public Livro atualizar(LivroUpdateRequest entityRequest) {
		Livro entity = consultaPorId(entityRequest.getId());

		if (!entity.getBiblioteca().getId().equals(entityRequest.getIdBiblioteca())) {
			throw new DadosInvalidosException(MessageTemplate.getCodigo(message.getBookNotBelongLibrary(), null));
		}

		List<Autor> autores = new ArrayList<Autor>();

		validaAutores(entityRequest, autores);

		if (!autores.isEmpty()) {
			entity.setAutores(autores);
		}

		entity.setISBN(entityRequest.getIsbn());
		entity.setTitulo(entityRequest.getTitulo());
		entity.setDataLancamento(entityRequest.getDataLancamento());

		super.save(entity);

		return entity;
	}

	public void delete(Integer id) {
		Livro entity = super.consultaPorId(id);

		super.delete(entity);
	}

	public Page<Autor> consultaPorIdLivro(Integer idLivro, Pageable page) {
		return autorService.consultaPorIdLivro(idLivro, page);
	}

	public void deleteAutorPorId(Integer idLivro, Integer idAutor) {
		Livro entity = super.consultaPorId(idLivro);

		this.getRepository().removeAutorPorId(entity.getId(), idAutor);
	}

	private void validaAutores(LivroCadastroRequest request, List<Autor> autores) {
		validaAutores(request.getIdAutores(), autores, request.getIdBiblioteca());
	}

	private void validaAutores(LivroUpdateRequest request, List<Autor> autores) {
		validaAutores(request.getIdAutores(), autores, request.getIdBiblioteca());
	}

	private void validaAutores(List<Integer> idsAutor, List<Autor> autores, Integer idBiblioteca) {
		if (null != idsAutor && !idsAutor.isEmpty()) {
			for (Integer id : idsAutor) {
				Autor entity = autorService.consultaPorId(id);

				if (!idBiblioteca.equals(entity.getBiblioteca().getId())) {
					throw new DadosInvalidosException(MessageTemplate.getCodigo(autorMessage.getAuthorNotBelongLibrary(), null));
				}

				autores.add(entity);
			}
		}
	}

}
