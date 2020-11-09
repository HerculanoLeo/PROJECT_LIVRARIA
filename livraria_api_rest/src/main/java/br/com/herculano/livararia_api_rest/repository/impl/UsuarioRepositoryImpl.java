package br.com.herculano.livararia_api_rest.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.herculano.livararia_api_rest.entity.Usuario;
import br.com.herculano.livararia_api_rest.repository.custom.UsuarioRespositoryCustom;
import br.com.herculano.livararia_api_rest.repository.utils.RepositoryUtils;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRespositoryCustom {

	@PersistenceContext
	private EntityManager em;
	
	
	@SuppressWarnings("unchecked")
	public Optional<Usuario> consultaPorEmailComTipo(String email) {
		
		String queryStr = "SELECT usu.id AS id_usuario, usu.email, usu.nome, usu.senha, bib.id AS id_biblioteca, op.id_operador AS id_operador FROM tb_usuario usu "
				+ "LEFT JOIN tb_biblioteca bib ON bib.id_usuario_administrador = usu.id "
				+ "LEFT JOIN tb_biblioteca_operador op ON op.id_operador = usu.id ";
		
		String where = RepositoryUtils.generateWhere("", " usu.email = '" + email + "'");
		
		queryStr += where;
		
		List<Object[]> rows = em.createNativeQuery(queryStr).getResultList();
		
		List<Usuario> entities = new ArrayList<Usuario>();
		
		for(Object[] row: rows) {
			
			Usuario entity = new Usuario();
			
			entity.setId((Integer) row[0]);
			entity.setEmail((String) row[1]);
			entity.setNome((String) row[2]);
			entity.setSenha((String) row[3]);
			
			if(null != row[4]) {
				
				entity.setTipo("bib");
				
			} else if(null != row[5]) {
				
				entity.setTipo("op");
				
			} else {
				
				entity.setTipo("com");
				
			}
			
			entities.add(entity);
			
		}
		
		Optional<Usuario> optional = Optional.ofNullable(entities.isEmpty() ? null : entities.get(0));
		
		return optional;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Page<Usuario> consultaOperadoresPorIdBiblioteca(Integer idBiblioteca, Pageable page) {
		String queryStr = "SELECT usu.id AS id_usuario, usu.email, usu.nome, usu.senha, op.id_biblioteca AS id_biblioteca, op.id_operador AS id_operador "
				+ "FROM tb_usuario usu "
				+ "INNER JOIN tb_biblioteca_operador op ON op.id_operador = usu.id ";
		
		String where = RepositoryUtils.generateWhere("", " op.id_biblioteca = '" + idBiblioteca + "'");
		
		queryStr += where;
		
		Long totalResgistros = RepositoryUtils.totalRegistros(queryStr, em);
		queryStr += RepositoryUtils.adicionarPaginacao(page);
		
		List<Object[]> rows = em.createNativeQuery(queryStr).getResultList();
		
		List<Usuario> entities = new ArrayList<Usuario>();
		
		for(Object[] row: rows) {
			
			Usuario entity = new Usuario();
			
			entity.setId((Integer) row[0]);
			entity.setEmail((String) row[1]);
			entity.setNome((String) row[2]);
			entity.setSenha((String) row[3]);
			
			if(null != row[4]) {
				
				entity.setTipo("bib");
				
			} 
			
			if(null != row[5]) {
				
				entity.setTipo("op");
				
			} else {
				
				entity.setTipo("com");
				
			}
			
			entities.add(entity);
		}
		
		
		return new PageImpl<Usuario>(entities, page, totalResgistros);
	}

}
