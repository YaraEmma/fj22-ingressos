package br.com.caelum.ingresso.validacao;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Lugar;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.descontos.DescontoEstudante;
import br.com.caelum.ingresso.model.descontos.TipoIngresso;

public class DescontoTest {

	@Test
	public void garanteQueODescontoDoEstudanteFoiAplicadoPelaMetadeDoPreco(){
		
		Filme filme = new Filme("A Cabana", Duration.ofMinutes(120), "Drama", new BigDecimal("20"));
		Sala sala = new Sala("Sala 2", new BigDecimal("20"));
		LocalTime horario = LocalTime.parse("10:00:00");
		Lugar lugar = new Lugar("A", 5);
		
		Sessao sessao = new Sessao(horario, sala, filme);
		Ingresso ingresso = new Ingresso(sessao, TipoIngresso.ESTUDANTE, lugar);
		
		BigDecimal precoEsperado = new BigDecimal("20");
		
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}
	
	@Test
	public void garanteQueOlugarA1EstaOcupadoEOLugarA2EA3EstaoLivres(){
		
		Lugar a1 = new Lugar("A", 1);
		Lugar a2 = new Lugar("A", 2);
		Lugar a3 = new Lugar("A", 3);
		
		Filme filme = new Filme("A Cabana", Duration.ofMinutes(120), "Drama", new BigDecimal("20"));
		Sala sala = new Sala("Sala 2", new BigDecimal("20"));
		LocalTime horario = LocalTime.parse("10:00:00");
		
		Sessao sessao = new Sessao(horario, sala, filme);
		Ingresso ingresso = new Ingresso(sessao, TipoIngresso.ESTUDANTE, a1);
		
		Set<Ingresso> ingressos = Stream.of(ingresso).collect(Collectors.toSet());
		sessao.setIngressos(ingressos);
		
		Assert.assertFalse(sessao.isDisponivel(a1));
		Assert.assertTrue(sessao.isDisponivel(a2));
		Assert.assertTrue(sessao.isDisponivel(a3));
	}
}
