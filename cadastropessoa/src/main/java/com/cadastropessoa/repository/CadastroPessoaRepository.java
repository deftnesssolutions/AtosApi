package com.cadastropessoa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cadastropessoa.models.Pessoa;

public interface CadastroPessoaRepository extends JpaRepository<Pessoa, String>{

}
