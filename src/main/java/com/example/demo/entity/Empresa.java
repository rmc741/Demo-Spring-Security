package com.example.demo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "company")
@NoArgsConstructor
@Getter
@Setter
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "company_id")
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(name = "name")
    private String nome;
    @Column(name = "cnpj")
    private String cnpj;
    @Column(name = "address")
    private String address;
    @Column(name = "balance")
    private BigDecimal balance;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
    private List<Funcionario> funcionariosList;

    public Empresa(String nome, String cnpj, String address, BigDecimal balance) {
        super();
        this.nome = nome;
        this.cnpj = cnpj;
        this.address = address;
        this.balance = balance;
    }
}