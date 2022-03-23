package com.example.demo.dto;

import com.example.demo.entity.Funcionario;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class FuncionarioDTO {

    private Long employee_id;
    private String nome;
    private String cpf;
    private String address;
    private Long company_id;
    private BigDecimal balance;

    public FuncionarioDTO(final Funcionario funcionario){
        this.employee_id = funcionario.getId();
        this.nome = funcionario.getNome();
        this.cpf = funcionario.getCpf();
        this.address = funcionario.getAddress();
        this.company_id = funcionario.getCompany().getId();
        this.balance = funcionario.getBalance();
    }
}
