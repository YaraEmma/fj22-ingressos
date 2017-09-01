package br.com.caelum.ingresso.validacao;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.descontos.DescontoEstudante;

public class DescontoTest {

	@Test
	public void garanteQueODescontoDoEstudanteFoiAplicadoPelaMetadeDoPreco(){
		Filme filme = new Filme("A Cabana", Duration.ofMinutes(120), "Drama", new BigDecimal("20"));
		Sala sala = new Sala("Sala 2", new BigDecimal("20"));
		LocalTime horario = LocalTime.parse("10:00:00");
		
		Sessao sessao = new Sessao(horario, sala, filme);
		Ingresso ingresso = new Ingresso(sessao, new DescontoEstudante());
		BigDecimal precoEsperado = new BigDecimal("20");
		
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}
}
