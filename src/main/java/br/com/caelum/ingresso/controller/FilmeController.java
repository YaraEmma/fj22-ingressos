package br.com.caelum.ingresso.controller;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.model.Filme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by nando on 03/03/17.
 */
@Controller
public class FilmeController {


    @Autowired
    private FilmeDao filmeDao;


    @GetMapping({"/admin/filme", "/admin/filme/{id}"})
    public ModelAndView form(@PathVariable("id") Optional<Integer> id, Filme filme){

        ModelAndView modelAndView = new ModelAndView("filme/filme");

        if (id.isPresent()){
            filme = filmeDao.findOne(id.get());
        }

        modelAndView.addObject("filme", filme);

        return modelAndView;
    }


    @PostMapping("/admin/filme")
    @Transactional
    public ModelAndView salva(@Valid Filme filme, BindingResult result){

        if (result.hasErrors()) {
            return form(Optional.ofNullable(filme.getId()), filme);
        }

        filmeDao.save(filme);

        ModelAndView view = new ModelAndView("redirect:/admin/filmes");

        return view;
    }


    @GetMapping(value="/admin/filmes")
    public ModelAndView lista(){

        ModelAndView modelAndView = new ModelAndView("filme/lista");

        modelAndView.addObject("filmes", filmeDao.findAll());

        return modelAndView;
    }


    @DeleteMapping("/admin/filme/{id}")
    @ResponseBody
    @Transactional
    public void delete(@PathVariable("id") Integer id){
        filmeDao.delete(id);
    }
    
    public static void main(String[] args) {
		
    	List<Filme> filme = new ArrayList<>();
    	
		Filme f = new Filme();
		Filme f2 = new Filme();
		Filme f3 = new Filme();
    	
    	f.setNome("A gala do pato");
    	f2.setNome("Teste, o filme");
    	f3.setNome("A coisa");
    	
    	filme.add(f);
    	filme.add(f2);
    	filme.add(f3);

//    	filme.removeIf(item -> item.getNome().startsWith("A"));
//    	filme.forEach(item -> System.out.println(item.getNome()));
    	
    	List<String> collect = filme.stream().map(item -> item.getNome()).collect(Collectors.toList());
    	collect.forEach(item -> System.out.println(item));
	}

}
