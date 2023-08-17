package br.com.autoparts.api.model;

import br.com.autoparts.enums.CargoEnum;

public record RegisterDTO (String nome, String senha, String email,Long cpf, Endereco endereco, CargoEnum cargo){}
