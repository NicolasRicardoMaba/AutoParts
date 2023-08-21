package br.com.autoparts.api.model;

import br.com.autoparts.enums.CargoEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "funcionarios")
@Getter
@Setter
public class Funcionario extends Pessoa{
    public Funcionario(String nome, Long cpf, String email, CargoEnum cargo) {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer funcionario_id;

    private Boolean cargoFuncionario;
    
}
