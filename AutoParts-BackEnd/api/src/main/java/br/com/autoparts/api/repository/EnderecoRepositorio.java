package br.com.autoparts.api.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.autoparts.api.model.Endereco;


public interface EnderecoRepositorio extends CrudRepository<Endereco, Integer> {
    
}
