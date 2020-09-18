package br.com.herculano.livararia_api_rest.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.herculano.livararia_api_rest.entity.GrupoUsuario;
import br.com.herculano.livararia_api_rest.repository.custom.GrupoUsuarioRepositoryCustom;

@Repository
public class GrupoUsuarioRepositoryImpl implements GrupoUsuarioRepositoryCustom{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<GrupoUsuario> consultaPorFiltro(GrupoUsuario filter, Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}
}
