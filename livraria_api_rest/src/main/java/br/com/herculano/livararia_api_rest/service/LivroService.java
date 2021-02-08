package br.com.herculano.livararia_api_rest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.herculano.livararia_api_rest.constants.system_message.AutorMessage;
import br.com.herculano.livararia_api_rest.constants.system_message.LivroMessage;
import br.com.herculano.livararia_api_rest.controller.request.livro.LivroCadastroRequest;
import br.com.herculano.livararia_api_rest.controller.request.livro.LivroConsultaRequest;
import br.com.herculano.livararia_api_rest.controller.request.livro.LivroUpdateRequest;
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
	

	public Page<Livro> consulta(LivroConsultaRequest entityRequest, Pageable page) {
		return getRepository().consulta(entityRequest, page);
	}


	public Livro cadastra(LivroCadastroRequest entityRequest) {
		List<Autor> autores = validaAutores(entityRequest.getIdAutores(), entityRequest.getIdBiblioteca());

		entityRequest.setBiblioteca(bibliotecaService.consultaPorId(entityRequest.getIdBiblioteca()));

		Livro entity = new Livro(entityRequest);

		if (!autores.isEmpty()) {
			entity.setAutores(autores);
		}

		super.save(entity);

		return entity;
	}

	public Livro atualizar(Integer idLivro, LivroUpdateRequest entityRequest) {
		Livro entity = consultaPorId(idLivro);

		List<Autor> autores = validaAutores(entityRequest.getIdAutores(), entity.getBiblioteca().getId());

		entity.setAutores(autores);

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
		Page<Autor> livros = autorService.consultaPorIdLivro(idLivro, page);

		return livros;
	}

	private List<Autor> validaAutores(List<Integer> idsAutor, Integer idBiblioteca) {
		List<Autor> autores = new ArrayList<>();

		if (null != idsAutor && !idsAutor.isEmpty()) {
			for (Integer id : idsAutor) {
				Autor entity = autorService.consultaPorId(id);

				if (!idBiblioteca.equals(entity.getBiblioteca().getId())) {
					throw new DadosInvalidosException(MessageTemplate.getCodigo(autorMessage.getAuthorNotBelongLibrary(), null));
				}

				autores.add(entity);
			}
		}

		return autores;
	}

}
