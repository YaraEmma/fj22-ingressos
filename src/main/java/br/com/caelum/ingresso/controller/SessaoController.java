package br.com.caelum.ingresso.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.form.SessaoForm;

@Controller
public class SessaoController {

	@Autowired
    private SalaDao salaDao;
	
    @Autowired
    private FilmeDao filmeDao;
    
    @Autowired
    private SessaoDao sessaoDao;
	
	@GetMapping("/admin/sessao")
    public ModelAndView form(@RequestParam("salaId") Integer salaId, SessaoForm form) {
		
		form.setSalaId(salaId);
		
        ModelAndView view = new ModelAndView("sessao/sessao");

        view.addObject("sala", salaDao.findOne(salaId));
        view.addObject("filmes", filmeDao.findAll());
        view.addObject("form", form);
        
        return view;
    }
	
	@PostMapping(value="/admin/sessao")
    @Transactional
    public ModelAndView salva(@Valid SessaoForm form, BindingResult result) {

        if (result.hasErrors()) 
        	return form(form.getSalaId(), form);

        Sessao sessao = form.toSessao(salaDao, filmeDao);
        sessaoDao.save(sessao);
        
        List<Sessao> sessoesSala = sessaoDao.buscaSessoesDaSala(sessao.getSala());
        GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoesSala);
        
        if(gerenciador.cabe(sessao)){
        	sessaoDao.save(sessao);
        	return new ModelAndView("redirect:/admin/sala/"+form.getSalaId()+"/sessoes/");
        }

        return form(form.getSalaId(), form);
    }

}
