package br.com.caelum.ingresso.model.descontos;

import java.math.BigDecimal;

public enum TipoIngresso {
	
	INTEIRA(new SemDesconto()),
	ESTUDANTE(new DescontoEstudante()),
	BANCOS(new DescontoBancos());
	
	private final Desconto desconto;
	
	TipoIngresso(Desconto desconto){
		this.desconto = desconto;
	}
	
	public BigDecimal aplicaDesconto(BigDecimal valor){
		return desconto.aplicaDesconto(valor);
	}
	
	public String getDescricao(){
		return desconto.getDescricao();
	}
}
