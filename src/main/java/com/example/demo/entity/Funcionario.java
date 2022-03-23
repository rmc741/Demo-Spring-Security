package com.example.demo.entity;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee")
@NoArgsConstructor
@Getter @Setter
public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id")
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(name = "name")
    private String nome;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "address")
    private String address;
    @Column(name = "balance")
    private BigDecimal balance;
    @ManyToOne
    @JoinColumn(name = "company", referencedColumnName = "company_id")
    private Empresa company;

    public Funcionario(String nome, String cpf, String address, BigDecimal balance, Empresa company) {
        super();
        this.nome = nome;
        this.cpf = cpf;
        this.address = address;
        this.balance = balance;
        this.company = company;
    }
}