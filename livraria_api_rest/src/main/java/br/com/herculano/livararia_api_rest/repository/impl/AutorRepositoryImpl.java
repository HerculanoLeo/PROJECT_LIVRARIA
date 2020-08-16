package br.com.herculano.livararia_api_rest.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.herculano.livararia_api_rest.entity.Autor;
import br.com.herculano.livararia_api_rest.repository.custom.AutorRepositoryCustom;
import br.com.herculano.livararia_api_rest.repository.utils.RepositoryUtils;

//O padrão de nomenclatura deve ser respeitado "EntityRepositoryImpl"
@Repository
public class AutorRepositoryImpl implements AutorRepositoryCustom{

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Autor> consultaPorIdLivro(Integer idLivro) {
		String queryStr = "SELECT aut.* FROM tb_autor aut"
				+ " INNER JOIN tb_livro_autor liv_aut ON liv_aut.id_autor = aut.id ";
		
		String where = RepositoryUtils.generateWhere("", "liv_aut.id_livro = '" + idLivro + "'");
		
		queryStr += where;
		
		Query query = em.createNativeQuery(queryStr, Autor.class);

		return query.getResultList();
	}
	
}
