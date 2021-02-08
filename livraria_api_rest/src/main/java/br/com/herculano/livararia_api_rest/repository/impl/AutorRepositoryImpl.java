package br.com.herculano.livararia_api_rest.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.herculano.livararia_api_rest.controller.request.autor.AutorConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.Autor;
import br.com.herculano.livararia_api_rest.repository.custom.AutorRepositoryCustom;
import br.com.herculano.utilities.repository.RepositoryUtils;

@Repository
public class AutorRepositoryImpl implements AutorRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	@SuppressWarnings("unchecked")
	public Page<Autor> consultaPorFiltro(AutorConsultaRequest entity, Pageable page) {
		String queryStr = "SELECT DISTINCT(a.*) FROM tb_autor a "
				+ " INNER JOIN tb_livro_autor l_a ON l_a.id_autor = a.id "
				+ " INNER JOIN tb_livro l ON l.id = l_a.id_livro" 
				+ " INNER JOIN tb_biblioteca b ON b.id = a.id_biblioteca";

		String where = "";
		Map<String, Object> params = new HashMap<>();

		if (StringUtils.isNotBlank(entity.getNome())) {
			where = RepositoryUtils.generateWhere(where, " UPPER(a.nome) LIKE :a");
			params.put("a", "%" + entity.getNome().toUpperCase() + "%");

		}

		if (StringUtils.isNotBlank(entity.getNomeLivro())) {
			where = RepositoryUtils.generateWhere(where, " UPPER(l.nome) LIKE :b");
			params.put("b", "%" + entity.getNomeLivro().toUpperCase() + "%");
		}

		if (null != entity.getDataNascimento()) {
			where = RepositoryUtils.generateWhere(where, " a.dt_nascimento >= :c");
			params.put("c", entity.getDataNascimento());
		}

		if (null != entity.getDataFalecimento()) {
			where = RepositoryUtils.generateWhere(where, " a.dt_nascimento <= :d");
			params.put("d", entity.getDataFalecimento());
		}

		if (StringUtils.isNotBlank(entity.getNomeBiblioteca())) {
			where = RepositoryUtils.generateWhere(where, " UPPER(b.nome) LIKE :e");
			params.put("e", "%" + entity.getNomeBiblioteca().toUpperCase() + "%");
		}

		if (null != entity.getIdBiblioteca() && entity.getIdBiblioteca() > 0) {
			where = RepositoryUtils.generateWhere(where, " b.id = :f");
			params.put("f", entity.getIdBiblioteca());
		}

		queryStr += where;

		queryStr += " ORDER BY a.id ASC";

		Long totalResgistros = RepositoryUtils.totalRegistros(queryStr, em, params);

		queryStr += RepositoryUtils.adicionarPaginacao(page);

		Query query = em.createNativeQuery(queryStr, Autor.class);

		for (Map.Entry<String, Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}

		List<Autor> entities = query.getResultList();

		return new PageImpl<Autor>(entities, page, totalResgistros);
	}

	@SuppressWarnings("unchecked")
	public Page<Autor> consultaPorIdLivro(Integer idLivro, Pageable page) {
		String queryStr = "SELECT a.* FROM tb_autor a" 
				+ " INNER JOIN tb_livro_autor l_a ON l_a.id_autor = a.id ";

		String where = RepositoryUtils.generateWhere("", "l_a.id_livro = :a ");

		Map<String, Object> params = new HashMap<>();

		params.put("a", idLivro);

		queryStr += where;

		queryStr += " ORDER BY a.id ASC";

		Long totalResgistros = RepositoryUtils.totalRegistros(queryStr, em, params);

		queryStr += RepositoryUtils.adicionarPaginacao(page);

		Query query = em.createNativeQuery(queryStr, Autor.class);

		for (Map.Entry<String, Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}

		List<Autor> entities = query.getResultList();

		return new PageImpl<Autor>(entities, page, totalResgistros);
	}

}
