package br.com.herculano.livararia_api_rest.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.herculano.livararia_api_rest.entity.Permissao;
import br.com.herculano.livararia_api_rest.repository.custom.PermissaoRepositoryCustom;
import br.com.herculano.utilities.repository.RepositoryUtils;

@Repository
public class PermissaoRepositoryImpl implements PermissaoRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	@SuppressWarnings("unchecked")
	public List<Permissao> consultaPorIdPerfil(Integer idPerfil) {
		String queryStr = "SELECT p.* FROM tb_permissao p "
				+ "INNER JOIN tb_perfil_permissao p_p ON p.codigo = p_p.id_permissao "
				+ "INNER JOIN tb_perfil pf ON p_p.id_perfil = pf.id ";
		
		String where = RepositoryUtils.generateWhere("", "pf.id = ?");
		
		queryStr += where;
		
		Query query = em.createNativeQuery(queryStr, Permissao.class);
		
		query.setParameter(1, idPerfil);

		return query.getResultList();
	}
	
	
}
