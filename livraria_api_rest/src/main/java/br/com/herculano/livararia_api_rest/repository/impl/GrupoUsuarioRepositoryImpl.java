package br.com.herculano.livararia_api_rest.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.herculano.livararia_api_rest.controller.request.GrupoUsuarioConsultaRequest;
import br.com.herculano.livararia_api_rest.entity.GrupoUsuario;
import br.com.herculano.livararia_api_rest.repository.custom.GrupoUsuarioRepositoryCustom;
import br.com.herculano.livararia_api_rest.repository.utils.RepositoryUtils;

@Repository
public class GrupoUsuarioRepositoryImpl implements GrupoUsuarioRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	@SuppressWarnings("unchecked")
	public Page<GrupoUsuario> consultaPorFiltro(GrupoUsuarioConsultaRequest filter, Pageable page) {
		String queryStr = "SELECT DISTINCT(gru.*) FROM tb_grupo_usuario gru"
				+ " LEFT JOIN tb_grupo_usuario_permissoes gru_per ON gru_per.id_grupo_usuario = gru.id";

		String where = "";

		if (StringUtils.isNotBlank(filter.getNome())) {
			where = RepositoryUtils.generateWhere(where, " UPPER(gru.nome) LIKE '%" + filter.getNome().toUpperCase() + "%'");
		}

		if (StringUtils.isNotBlank(filter.getPermissao())) {
			where = RepositoryUtils.generateWhere(where, "UPPER(gru_per.id_permissao) LIKE '%" + filter.getPermissao().toUpperCase() + "%'");
		}

		queryStr += where;

		Long totalResgistros = RepositoryUtils.totalRegistros(queryStr, em);
		queryStr += RepositoryUtils.adicionarPaginacao(page);

		List<GrupoUsuario> entities = em.createNativeQuery(queryStr, GrupoUsuario.class).getResultList();

		return new PageImpl<>(entities, page, totalResgistros);
	}
}
