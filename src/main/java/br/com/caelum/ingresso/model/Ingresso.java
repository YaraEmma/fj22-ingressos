package br.com.caelum.ingresso.model;

import java.math.BigDecimal;

import br.com.caelum.ingresso.model.descontos.Desconto;

public class Ingresso {
	
	private Sessao sessao;
	private BigDecimal preco;
	
	/**
	 * @deprecated hibernate only
	 */
	public Ingresso(){
		
	}
	
	public Ingresso(Sessao sessao, Desconto desconto) {
		this.sessao = sessao;
		this.preco = desconto.aplicaDesconto(sessao.getPreco());
	}
	
	public Sessao getSessao() {
		return sessao;
	}
	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

	public BigDecimal getPreco() {
		return preco;
	}
}
