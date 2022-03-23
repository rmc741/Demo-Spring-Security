package com.example.demo.dto;

import com.example.demo.entity.Empresa;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class EmpresaDTO {

    private Long company_id;
    private String nome;
    private String cnpj;
    private String address;
    private BigDecimal balance;

    public EmpresaDTO(final Empresa company){
        super();
        this.company_id = company.getId();
        this.nome = company.getNome();
        this.cnpj = company.getCnpj();
        this.address = company.getAddress();
        this.balance = company.getBalance();
    }

}
