package br.com.javamagazine.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.javamagazine.entity.Produto;

public class ProdutoServices {
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Produto> findAll(){
		return em.createQuery("SELECT p FROM Produto p ORDER BY p.titulo").getResultList();
	}
}
