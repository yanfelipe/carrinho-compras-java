package br.com.javamagazine.web;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.javamagazine.entity.Produto;
import br.com.javamagazine.services.ProdutoServices;

@ManagedBean
@RequestScoped
public class ProdutoMB {
	private ProdutoServices produtoServices;
	private List<Produto> produtos;
	
	@PostConstruct
	public void init(){
		produtos = produtoServices.findAll();
	}
	
	public List<Produto> getProdutos(){
		return produtos;
	}
}
