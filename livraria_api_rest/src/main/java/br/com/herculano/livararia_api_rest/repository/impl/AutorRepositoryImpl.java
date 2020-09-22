package br.com.herculano.livararia_api_rest.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.herculano.livararia_api_rest.controller.request.AutorConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.Autor;
import br.com.herculano.livararia_api_rest.repository.custom.AutorRepositoryCustom;
import br.com.herculano.livararia_api_rest.repository.utils.RepositoryUtils;

//O padrão de nomenclatura deve ser respeitado "EntityRepositoryImpl"
@Repository
public class AutorRepositoryImpl implements AutorRepositoryCustom{

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public Page<Autor> consultaPorIdLivro(Integer idLivro, Pageable page) {
		String queryStr = "SELECT aut.* FROM tb_autor aut"
				+ " INNER JOIN tb_livro_autor liv_aut ON liv_aut.id_autor = aut.id ";
		
		String where = RepositoryUtils.generateWhere("", "liv_aut.id_livro = '" + idLivro + "'");
		
		queryStr += where;
		
		queryStr += " ORDER BY aut.id ASC";
		
		Long totalResgistros = RepositoryUtils.totalRegistros(queryStr, em);
		queryStr += RepositoryUtils.adicionarPaginacao(page);
		
		List<Autor> entities = em.createNativeQuery(queryStr, Autor.class).getResultList();
		
		return new PageImpl<Autor>(entities, page, totalResgistros);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Page<Autor> consultaPorFiltro(AutorConsultaRequest entity, Pageable page) {
		String queryStr = "SELECT aut.* FROM tb_autor aut "
				+ " INNER JOIN tb_livro_autor liv_aut ON liv_aut.id_autor = aut.id "
				+ " INNER JOIN tb_livro liv ON liv.id = liv_aut.id_livro";
		
		String where = "";
		
		if(StringUtils.isNotBlank(entity.getNome())) {
			where = RepositoryUtils.generateWhere(where, " aut.nome LIKE '%" + entity.getNome() + "%'");
		}
		
		if(StringUtils.isNotBlank(entity.getNomeLivro())) {
			where = RepositoryUtils.generateWhere(where, " liv.nome LIKE '%"+ entity.getNomeLivro() + "%'");
		}
		
		if(null != entity.getDataNascimento()) {
			where = RepositoryUtils.generateWhere(where, " aut.dt_nascimento >= '" + entity.getDataNascimento() + "'");
		}
		
		if(null != entity.getDataFalecimento()) {
			where = RepositoryUtils.generateWhere(where, " aut.dt_nascimento <= '" + entity.getDataFalecimento() + "'");
		}
		
		queryStr += where;
		
		queryStr += " ORDER BY aut.id ASC";
		
		Long totalResgistros = RepositoryUtils.totalRegistros(queryStr, em);
		queryStr += RepositoryUtils.adicionarPaginacao(page);
		
		List<Autor> entities = em.createNativeQuery(queryStr, Autor.class).getResultList();
		
		return new PageImpl<Autor>(entities, page, totalResgistros);
	}
	
}
