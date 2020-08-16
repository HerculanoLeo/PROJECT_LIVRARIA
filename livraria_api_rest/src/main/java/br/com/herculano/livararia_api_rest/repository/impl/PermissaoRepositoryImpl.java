package br.com.herculano.livararia_api_rest.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.herculano.livararia_api_rest.entity.Permissao;
import br.com.herculano.livararia_api_rest.repository.custom.PermissaoRepositoryCustom;
import br.com.herculano.livararia_api_rest.repository.utils.RepositoryUtils;

@Repository
public class PermissaoRepositoryImpl implements PermissaoRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	@SuppressWarnings("unchecked")
	public List<Permissao> consultaPorIdUsuario(Integer idUsuario) {
		String queryStr = "SELECT per.* FROM tb_permissoes per "
				+ "INNER JOIN tb_grupo_usuario_permissoes gru_per ON gru_per.id_permissao = per.codigo "
				+ "INNER JOIN tb_grupo_usuario gru_usu ON gru_usu.id = gru_per.id_grupo_usuario "
				+ "INNER JOIN tb_usuario_grupo_usuario usu_gru ON usu_gru.id_grupo_usuario = gru_usu.id "
				+ "INNER JOIN tb_usuario usu ON usu.id = usu_gru.id_usuario ";
		
		String where = RepositoryUtils.generateWhere("", "usu.id = '" + idUsuario + "'");
		
		queryStr += where;
		
		Query query = em.createNativeQuery(queryStr, Permissao.class);

		return query.getResultList();
	}
	
	
}
