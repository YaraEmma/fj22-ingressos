package br.com.caelum.ingresso.validacao;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.ingresso.controller.GerenciadorDeSessao;
import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessaoTest {

	@Test
	public void garanteQueNaoDevePermitirSessaoNoMesmoHorario(){
		
		Filme filme = new Filme("A Bela e a Fera", Duration.ofMinutes(120), "SCI-FI", new BigDecimal("10.0"));
		LocalTime horario = LocalTime.parse("10:00:00");
		
		Sala sala = new Sala("", new BigDecimal("20.0"));
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario, sala, filme));
		
		Sessao sessao = new Sessao(horario, sala, filme);
		
		GerenciadorDeSessao ger = new GerenciadorDeSessao(sessoes);
		
		Assert.assertFalse(ger.cabe(sessao));
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessoesTerminandoDentroDoHorarioDeUmaSessaoExistente(){
		
		Filme filme = new Filme("A Cabana", Duration.ofMinutes(120), "SCI-FI", new BigDecimal("10.0"));
		LocalTime horario = LocalTime.parse("10:00:00");
		
		Sala sala = new Sala("", new BigDecimal("20.0"));
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario, sala, filme));
		
		Sessao sessao = new Sessao(horario.minusHours(1), sala, filme);
		GerenciadorDeSessao ger = new GerenciadorDeSessao(sessoes);
		
		Assert.assertFalse(ger.cabe(sessao));
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessoesIniciandoDentroDoHorarioDeUmaSessaoExistente(){
		
		Filme filme = new Filme("Margarida", Duration.ofMinutes(120), "SCI-FI", new BigDecimal("10.0"));
		LocalTime horario = LocalTime.parse("10:00:00");
		Sala sala = new Sala("", new BigDecimal("10.0"));
		
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario, sala, filme));
		
		Sessao sessao = new Sessao(horario.plusHours(1), sala, filme);
		GerenciadorDeSessao ger = new GerenciadorDeSessao(sessoes);
		
		Assert.assertFalse(ger.cabe(sessao));
	}
	
	@Test
	public void garanteQueDevePermitirUmaInsercaoEntreDoisFilmes(){
		
		Sala sala = new Sala("", new BigDecimal("10.0"));
		
		Filme filme1 = new Filme("Margarida", Duration.ofMinutes(90), "SCI-FI", new BigDecimal("10.0"));
		LocalTime dezHoras = LocalTime.parse("10:00:00");
		Sessao sessaoDasDez = new Sessao(dezHoras, sala, filme1);
		
		Filme filme2 = new Filme("Margarida", Duration.ofMinutes(120), "SCI-FI", new BigDecimal("10.0"));
		LocalTime dezoitoHoras = LocalTime.parse("18:00:00");
		Sessao sessaoDasDezoito = new Sessao(dezoitoHoras, sala, filme2);
		
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez, sessaoDasDezoito);
		
		GerenciadorDeSessao ger = new GerenciadorDeSessao(sessoes);
		
		Assert.assertTrue(ger.cabe(new Sessao(LocalTime.parse("13:00:00"), sala, filme2)));
	}
	
	@Test
	public void garantirQueOValorDaSessaoSejaIgualASomaDoValorDoFilmeComOValorDaSala(){
		
		LocalTime horarioFilme = LocalTime.parse("10:00:00");
		Sala sala = new Sala("Sala 2", new BigDecimal("20.0"));
		Filme filme = new Filme("O Planeta Dos Macacos", Duration.ofMinutes(120), "Acao", new BigDecimal("20.0"));
		
		BigDecimal somaDosPrecosDaSalaEFilme = sala.getPreco().add(filme.getPreco());
		Sessao sessao = new Sessao(horarioFilme, sala, filme);
		
		Assert.assertEquals(somaDosPrecosDaSalaEFilme, sessao.getPreco());
	}
}
