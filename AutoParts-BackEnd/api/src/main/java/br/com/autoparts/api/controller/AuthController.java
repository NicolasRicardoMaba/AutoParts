package br.com.autoparts.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.autoparts.api.model.AuthenticationDTO;
import br.com.autoparts.api.model.Cliente;
import br.com.autoparts.api.model.RegisterDTO;
import br.com.autoparts.api.repository.ClienteRepositorio;
import br.com.autoparts.api.repository.FuncionarioRepositorio;
import br.com.autoparts.enums.CargoEnum;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authManager;
    private ClienteRepositorio ClientRepository;
    private FuncionarioRepositorio funcionaryRepository;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(),data.password());
        var auth = this.authManager.authenticate(usernamePassword);   
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cadastro")
    public ResponseEntity cadastrar(@RequestBody RegisterDTO data){
        if (data.cargo() == CargoEnum.CLIENTE)
        {
    String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
    Cliente novoCliente = new Cliente(data.nome(),data.cpf(), data.email(), data.cargo());   

    }
        
        return null;

    }


}
