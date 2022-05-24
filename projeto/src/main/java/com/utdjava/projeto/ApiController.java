package com.utdjava.projeto;

import java.util.Comparator;
import java.util.List;

import com.utdjava.projeto.entidade.Livro;
import com.utdjava.projeto.servico.LivroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApiController {
	
	@Autowired
	private LivroService servico;
		
	@RequestMapping("/")
	public String paginaInicial(Model model) {
		//pegando os dados de dentro do banco.
		List<Livro> livros = servico.listarTodos();
		
		//organizando por ordem crescente.
		livros.sort(Comparator.comparingLong(Livro::getId));
		
		//jogando os dados na página através de um model.
		model.addAttribute("listalivros",livros);
		
		return "index";
	}
	
	@RequestMapping("/novo")
	public String novoProduto(Model model) {
		
		Livro livro = new Livro();
		model.addAttribute("livro",livro);
		
		return "cadastrolivro";
	}
	
	@RequestMapping(value="/salvar", method=RequestMethod.POST)
	public String salvar(@ModelAttribute("livro") Livro livro) {
	
		servico.adicionar(livro);
		return "redirect:/";
	}
	
	@RequestMapping("/editar/{id}")
	public ModelAndView paginaEditar(@PathVariable(name="id") long id) {
		ModelAndView mav = new ModelAndView("editarlivro");
		
		Livro livro = servico.modificar(id);
		
		mav.addObject("livro", livro);
		
		return mav;
	}
	
	@RequestMapping("/deletar/{id}")
	public String deletar(@PathVariable(name="id") long id) {
	
		servico.deletar(id);
		return "redirect:/";
	}
}

