package br.com.autoparts.api.controller;

import br.com.autoparts.api.model.AuthenticationDTO;
import br.com.autoparts.api.model.Cliente;
import br.com.autoparts.api.model.Funcionario;
import br.com.autoparts.api.model.RegisterDTO;
import br.com.autoparts.api.repository.ClienteRepositorio;
import br.com.autoparts.api.repository.FuncionarioRepositorio;
import br.com.autoparts.enums.CargoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authManager;
  @Autowired
  private ClienteRepositorio ClientRepository;  
  @Autowired
  private FuncionarioRepositorio funcionaryRepository;

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody AuthenticationDTO data) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(
      data.login(),
      data.password()
    );
    var auth = this.authManager.authenticate(usernamePassword);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/cadastro")

  public ResponseEntity cadastrar(@RequestBody RegisterDTO data) {
  
    if (data.cargo() == CargoEnum.CLIENTE){ 
        if (ClientRepository.findByEmail(data.email())!= null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      } else {      
    String encryptedPassword = new BCryptPasswordEncoder()
        .encode(data.senha());
    Cliente novoCliente = new Cliente(
        data.nome(),
        data.cpf(),
        data.email(),
        data.cargo(),
        data.endereco()    
        );
      return new ResponseEntity<>(HttpStatus.CREATED);
      }  

    }  
    else if  
  ((data.cargo() == CargoEnum.FUNCIONARIO) || (data.cargo() == CargoEnum.ADMIN)){

  if (funcionaryRepository.findByEmail(data.email())!= null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      } else {      
   String encryptedPassword = new BCryptPasswordEncoder()
        .encode(data.senha());
    Funcionario  funcionario= new Funcionario(
        data.nome(),
        data.cpf(),
        data.email(),
        data.cargo()
        
        );
      return new ResponseEntity<>(HttpStatus.CREATED);

      }  
      
      }
  else  return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

  
}
}

