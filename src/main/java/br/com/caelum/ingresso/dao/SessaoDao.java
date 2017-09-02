package br.com.caelum.ingresso.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

@Repository
public class SessaoDao {

	@PersistenceContext
    private EntityManager em;
	
	public void save(Sessao sessao) {
        em.persist(sessao);
    }
	
	public List<Sessao> buscaSessoesDaSala(Sala sala) {
        return em.createQuery("select s from Sessao s where s.sala = :sala", Sessao.class)
        		.setParameter("sala", sala)
        		.getResultList();
    }

	public List<Sessao> buscaSessoesDoFilme(Filme filme) {
		return em.createQuery("select s from Sessao s where s.filme = :filme", Sessao.class)
        		.setParameter("filme", filme)
        		.getResultList();
	}
	
	public Sessao findOne(Integer id){
		return em.find(Sessao.class, id);
	}
}
