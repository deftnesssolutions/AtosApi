package com.cadastropessoa.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cadastropessoa.models.Pessoa;
import com.cadastropessoa.repository.CadastroPessoaRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(value="API REST Cadastro pessoa")
@RestController
@RequestMapping("/cadastro")
public class CadastroPessoaResource {
	@Autowired
	private CadastroPessoaRepository cpr;
	
	@ApiOperation(value="Retorna uma lista de pessoas")
	@GetMapping(produces="application/json")
	public @ResponseBody Iterable<Pessoa> listaPessoas(){
		Iterable<Pessoa> listaPessoas = cpr.findAll();
		return listaPessoas;
	}
	
	@ApiOperation(value="Registra uma pessoa")
	@PostMapping()
	public Pessoa cadastraPessoa(@RequestBody @Valid Pessoa pessoa) {
		return cpr.save(pessoa);
	}
	
	@ApiOperation(value="Deleta uma pessoa")
	@DeleteMapping()
	public Pessoa deletaPessoa(@RequestBody Pessoa pessoa) {
		cpr.delete(pessoa);
		return pessoa;
	}
}
