package br.com.autoparts.api.service;

import java.security.KeyPair;
import java.security.SignatureException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import br.com.autoparts.api.model.Cliente;
import br.com.autoparts.api.model.Funcionario;
import br.com.autoparts.api.model.Pessoa;
import br.com.autoparts.api.model.Retorno;
import br.com.autoparts.api.repository.ClienteRepositorio;
import br.com.autoparts.api.repository.FuncionarioRepositorio;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service    
public class Servico  {
    private String secretKey = "mySecretKey";

    @Autowired
    private Retorno retorno;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;
 
    public ResponseEntity<?> verificarUser(Pessoa p) {
        if (p.getEmail() == null || p.getSenha() == null) {
            retorno.setMensagem("Valores de senha ou/e email nulo!");
            return new ResponseEntity<>(retorno.getMensagem(), HttpStatus.BAD_REQUEST);
        } else {
            List<Cliente> clienteByEmail = clienteRepositorio.findByEmail(p.getEmail());
            List<Cliente> clienteBySenha = clienteRepositorio.findBySenha(p.getSenha());

            if (!clienteByEmail.isEmpty() && !clienteBySenha.isEmpty()) {
                return new ResponseEntity<>(clienteByEmail.get(0), HttpStatus.OK);
            }

            List<Funcionario> funcionariosByEmail = funcionarioRepositorio.findByEmail(p.getEmail());
            List<Funcionario> funcionariosBySenha = funcionarioRepositorio.findBySenha(p.getSenha());

            if (!funcionariosByEmail.isEmpty() && !funcionariosBySenha.isEmpty()) {
                return new ResponseEntity<>(funcionariosByEmail.get(0), HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


        public ResponseEntity<?> geraToken(Pessoa p) {
            HttpStatus userVerificado = (HttpStatus) verificarUser(p).getStatusCode();
            System.out.println(userVerificado);
            if (HttpStatus.OK.equals(userVerificado)) {
                try {

                    // Defina a duração do token em minutos (exemplo: 30 minutos)
                    int tokenExpirationMinutes = 30;
        
                    // Calcule a data de expiração com base na duração em minutos
                    Date expirationDate = new Date(System.currentTimeMillis() + (tokenExpirationMinutes * 60000));
        
                   KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);

                    // Gere um token JWT com o email do usuário como payload
                    String token = Jwts.builder()
                            .setSubject(p.getEmail()) // Define o email como assunto (subject) do token
                            .setExpiration(expirationDate) // Define a data de expiração do token
                            .signWith(keyPair.getPrivate())
                            .compact();
        
                    // Retorna o token gerado
                    return new ResponseEntity<>(token, HttpStatus.OK);
                } catch (Exception e) {
                    // Em caso de erro na geração do token, você pode retornar uma resposta de erro adequada.
                    return new ResponseEntity<>("Erro ao gerar o token.", HttpStatus.INTERNAL_SERVER_ERROR);
                }
                
            } else {
                // Caso o usuário não seja verificado, você pode retornar um erro ou outra resposta adequada.
                return new ResponseEntity<>("Usuário não encontrado ou não autorizado.", HttpStatus.UNAUTHORIZED);
            }
        }
        

    public ResponseEntity<?> validarToken(String token) throws SignatureException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .parseClaimsJws(token)
                    .getBody();

            // Verifique se o token ainda não expirou
            Date expirationDate = claims.getExpiration();
            if (expirationDate.before(new Date())) {
                return new ResponseEntity<>("Token expirado", HttpStatus.UNAUTHORIZED);
            }

            // As informações do usuário podem ser acessadas através de claims
            String email = claims.getSubject();
            // List<Funcionario> funcionariosByEmail = funcionarioRepositorio.findByEmail(p.getEmail());
            //List<Funcionario> funcionariosBySenha = funcionarioRepositorio.findBySenha(p.getSenha());
            Funcionario funcionario = (Funcionario) funcionarioRepositorio.findByEmail(email);

            if (funcionario != null){
                        return new ResponseEntity<>("Token válido para o usuário: " + email, HttpStatus.OK);

            }else
            {
                Cliente cliente = (Cliente) clienteRepositorio.findByEmail(email);
                    if (cliente != null){

                        return new ResponseEntity<>("Token válido para o usuário: " + email, HttpStatus.OK);


                }
            }
            // Token válido
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("Token expirado", HttpStatus.UNAUTHORIZED);
        } catch (MalformedJwtException e) {
            return new ResponseEntity<>("Token inválido", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro na validação do token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return null;
         
    }
}
        
        

