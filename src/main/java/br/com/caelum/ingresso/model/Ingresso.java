package br.com.caelum.ingresso.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.caelum.ingresso.model.descontos.Desconto;
import br.com.caelum.ingresso.model.descontos.TipoIngresso;

@Entity
public class Ingresso {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private Sessao sessao;
	
	@ManyToOne
	private Lugar lugar;

	private BigDecimal preco;
	
	@Enumerated(EnumType.STRING)
	private TipoIngresso tipoIngresso;
	
	/**
	 * @deprecated hibernate only
	 */
	public Ingresso(){
		
	}
	
	public Ingresso(Sessao sessao, TipoIngresso tipoIngresso, Lugar lugar) {
		this.sessao = sessao;
		this.tipoIngresso = tipoIngresso;
		this.preco = this.tipoIngresso.aplicaDesconto(sessao.getPreco());
		this.lugar = lugar;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public TipoIngresso getTipoIngresso() {
		return tipoIngresso;
	}

	public void setTipoIngresso(TipoIngresso tipoIngresso) {
		this.tipoIngresso = tipoIngresso;
	}

	public Lugar getLugar() {
		return lugar;
	}

	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
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
