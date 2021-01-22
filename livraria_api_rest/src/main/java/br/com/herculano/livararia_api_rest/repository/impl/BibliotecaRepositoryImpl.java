package br.com.herculano.livararia_api_rest.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.herculano.livararia_api_rest.controller.request.BibliotecaConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.Biblioteca;
import br.com.herculano.livararia_api_rest.repository.custom.BibliotecaRespositoryCustom;
import br.com.herculano.utilities.repository.RepositoryUtils;

@Repository
public class BibliotecaRepositoryImpl implements BibliotecaRespositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public Page<Biblioteca> consultaPorFiltro(BibliotecaConsultaRequest entityRequest, Pageable page) {
		String queryStr = "SELECT bib.* FROM tb_biblioteca bib "
				+ "INNER JOIN tb_usuario usu ON bib.id_usuario_administrador = usu.id";

		String where = "";

		if (StringUtils.isNotBlank(entityRequest.getNomeBiblioteca())) {
			where = RepositoryUtils.generateWhere(where,
					"UPPER(bib.nome) LIKE '%" + entityRequest.getNomeBiblioteca().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(entityRequest.getNomeAdministrador())) {
			where = RepositoryUtils.generateWhere(where,
					"UPPER(usu.nome) LIKE '%" + entityRequest.getNomeAdministrador().toUpperCase() + "%'");
		}

		queryStr += where;

		queryStr += " ORDER BY bib.id ASC";

		Long totalResgistros = RepositoryUtils.totalRegistros(queryStr, em);
		
		queryStr += RepositoryUtils.adicionarPaginacao(page);
		
		List<Biblioteca> entities = em.createNativeQuery(queryStr, Biblioteca.class).getResultList();

		return new PageImpl<Biblioteca>(entities, page, totalResgistros);
	}

	@Override
	public Biblioteca consultaPorUsuarioId(Integer idUsuario) {
		String queryStr = "SELECT bib.* FROM tb_biblioteca bib "
				+ "INNER JOIN tb_usuario usu ON bib.id_usuario_administrador = usu.id";

		String where = RepositoryUtils.generateWhere("", "bib.id_usuario_administrador = " + idUsuario + "");

		queryStr += where;

		return (Biblioteca) em.createNativeQuery(queryStr, Biblioteca.class).getSingleResult();
	}

}
