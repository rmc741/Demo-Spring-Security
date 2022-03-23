package com.example.demo.service;

import com.example.demo.dto.FuncionarioDTO;
import com.example.demo.entity.Empresa;
import com.example.demo.entity.Funcionario;
import com.example.demo.exception.DataIntegrityViolationException;
import com.example.demo.exception.ObjectNotFoundException;
import com.example.demo.repository.EmpresaRepository;
import com.example.demo.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FuncionarioService {

    private FuncionarioRepository funcionarioRepository;
    private EmpresaRepository empresaRepository;

    public List<Funcionario> buscarTodos(){
        return funcionarioRepository.findAll();
    }

    public Funcionario buscarFunciornario(final Long id){
        Optional<Funcionario> employee = funcionarioRepository.findById(id);
        return employee.orElseThrow(() -> new ObjectNotFoundException("Funcionario não encontrado!"));
    }

    public Funcionario inserir(final FuncionarioDTO funcionarioDTO){
        if(findByCpf(funcionarioDTO) != null){
            throw new DataIntegrityViolationException("Funcionario ja cadastrado!");
        }

        Optional<Empresa> company = empresaRepository.findById(funcionarioDTO.getCompany_id());
        return funcionarioRepository.save(new Funcionario(funcionarioDTO.getNome() , funcionarioDTO.getCpf() , funcionarioDTO.getAddress(),
                funcionarioDTO.getBalance() , company.get() ));
    }

    public Funcionario atualizarDados(final Long employeeId , @Valid FuncionarioDTO funcionarioDTO){
        Funcionario employee = buscarFunciornario(employeeId);
        if(findByCpf(funcionarioDTO) != null && findByCpf(funcionarioDTO).getId() != employeeId){
            throw new DataIntegrityViolationException("CPF ja cadastrado!");
        }

        Optional<Empresa> company = empresaRepository.findById(funcionarioDTO.getCompany_id());

        employee.setNome(funcionarioDTO.getNome());
        employee.setAddress(funcionarioDTO.getAddress());
        employee.setCpf(funcionarioDTO.getCpf());
        employee.setBalance(funcionarioDTO.getBalance());
        employee.setCompany(company.get());

        return funcionarioRepository.save(employee);
    }

    public void remover(final Long employeeId){
        Funcionario employee = buscarFunciornario(employeeId);
        funcionarioRepository.delete(employee);
    }

    public Funcionario consultarSaldo(final Long employeeId){
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(employeeId);
        if(funcionarioOpt.isPresent()){ return funcionarioOpt.get(); }
        throw new ObjectNotFoundException("Funcionario não encontrado");
    }

    private Funcionario findByCpf(final FuncionarioDTO funcionarioDTO) {
        Funcionario employee = funcionarioRepository.findByCpf(funcionarioDTO.getCpf());
        if(employee != null){ return employee; }
        return null;
    }
}
