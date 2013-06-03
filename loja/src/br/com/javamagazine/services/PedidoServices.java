package br.com.javamagazine.services;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.javamagazine.entity.Cliente;
import br.com.javamagazine.entity.Pedido;
import br.com.javamagazine.exception.ClienteNaoEncontradoException;

public class PedidoServices {
	@PersistenceContext
	private EntityManager em;
	
	private ClienteServices clienteServices;
	
	public Pedido criarPedido(Pedido pedido, String emailCliente, String senhaCliente) throws ClienteNaoEncontradoException{
		Cliente cliente = clienteServices.findByEmailAndSenha(emailCliente, senhaCliente);
		
		if(cliente==null){
			throw new ClienteNaoEncontradoException();
		}
		
		pedido.setData(new Date());
		pedido.setCliente(cliente);
		em.persist(pedido);
		
		return pedido;
	}
}
