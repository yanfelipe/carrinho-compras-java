package br.com.javamagazine.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ItemPedido implements Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="id_produto")
	private Produto produto;
	
	@Column(name="preco_unitario", nullable=false)
	private Double precoUnitario;
	
	@Column(nullable=false)
	private Integer quantidade;
	
	@Column(name="preco_total", nullable=false)
	private Double precoTotal;

	//getters e setters
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Double getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(Double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(Double precoTotal) {
		this.precoTotal = precoTotal;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	//construtor padrão e métodos de negócio
	public ItemPedido(){}
	
	public ItemPedido(Produto produto){
		this.produto = produto;
	}
	
	public ItemPedido(Produto produto, Integer quantidade){
		this.produto = produto;
		this.precoUnitario = produto.getPreco();
		this.quantidade = quantidade;
		calcularTotal();
	}
	
	public void calcularTotal(){
		precoTotal = precoUnitario * quantidade;
	}
	
	public void atualizarQuantidade(Integer novaQuantidade){
		this.quantidade = novaQuantidade;
		calcularTotal();
	}
	
	public int compareTo(ItemPedido itemPedido){
		return produto.getTitulo().compareTo(itemPedido.getProduto().getTitulo());
	}
}
