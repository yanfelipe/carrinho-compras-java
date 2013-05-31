package br.com.javamagazine.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="pedido")
public class Pedido implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_pedido", nullable=false)
	private Date data;
	
	@ManyToOne
	@JoinColumn(name="id_cliente")
	@Column(nullable=false)
	private Cliente cliente;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="item_pedido", joinColumns = @JoinColumn(name="id_pedido"))
	private Set<ItemPedido> itens;
	
	@Column(name="total", nullable=false)
	private Double valorTotal;

	//getters e setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	//caso a variavel "itens" esteja vazia, inicia esta variável resgatando os ItemPedido instanciados.
	public Set<ItemPedido> getItens() {
		if(itens==null){
			itens = new HashSet<ItemPedido>();
		}
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	//métodos de negócio
	public List<ItemPedido> getItensOrdenadosEmLista(){
		return new ArrayList<ItemPedido>(getItens());
	}
	
	public void adicionarItem(Produto produto, Integer quantidade){
		ItemPedido itemExistente = getItem(produto);
		if(itemExistente != null){
			atualizarQuantidade(produto, itemExistente.getQuantidade() + quantidade);
		}
		else{
			getItens().add(new ItemPedido(produto, quantidade));
			calcularTotal();
		}
	}
	
	public void removerItem(Produto produto){
		getItens().remove(new ItemPedido(produto));
		calcularTotal();
	}
	
	private ItemPedido getItem(Produto produto) {
		ItemPedido itemAProcurar = new ItemPedido(produto);
		
		for(ItemPedido item : getItens()){
			if(item.equals(itemAProcurar)){
				return item;
			}
		}
		return null;
	}
	
	public void atualizarQuantidade(Produto produto, Integer novaQuantidade){
		ItemPedido item = getItem(produto);
		
		if(item==null){
			throw new IllegalArgumentException("Item não encontrado para o produto: " + produto);
		}
		
		item.atualizarQuantidade(novaQuantidade);
		calcularTotal();
	}
	
	public void calcularTotal(){
		valorTotal = 0D;
		
		for(ItemPedido item : getItens()){
			valorTotal += item.getPrecoTotal();
		}
	}
}
