package com.example.demo.service;

import com.example.demo.entity.Empresa;
import com.example.demo.entity.Funcionario;
import com.example.demo.repository.EmpresaRepository;
import com.example.demo.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TransacaoService {

    private final EmpresaRepository empresaRepository;
    private final FuncionarioRepository funcionarioRepository;

    public Empresa sacar(Long id, BigDecimal valor_transacao) {
        Optional<Empresa> empresaOpt = empresaRepository.findById(id);
        Empresa company = empresaOpt.get();

        company.setBalance(empresaOpt.get().getBalance());
        if(valor_transacao.compareTo(BigDecimal.ZERO) == 1 && valor_transacao.compareTo(company.getBalance()) <= 0){
            company.setBalance(company.getBalance().subtract(valor_transacao));
            return company;
        }
        return null;
    }

    public Funcionario depositar(final Long id, final BigDecimal valor) {
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(id);
        Funcionario employee = funcionarioOpt.get();

        if(valor.compareTo(BigDecimal.ZERO) == 1){
            employee.setBalance(employee.getBalance().add(valor));
            return employee;
        }
        return null;
    }
}
