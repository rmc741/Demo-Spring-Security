package com.example.demo.service;

import com.example.demo.dto.EmpresaDTO;
import com.example.demo.entity.Empresa;
import com.example.demo.entity.Funcionario;
import com.example.demo.exception.DataIntegrityViolationException;
import com.example.demo.exception.ObjectNotFoundException;
import com.example.demo.repository.EmpresaRepository;
import com.example.demo.repository.FuncionarioRepository;
import com.example.demo.resource.request.TransacaoRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmpresaService {


    private final EmpresaRepository empresaRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final TransacaoService transacaoService;

    public EmpresaService(EmpresaRepository empresaRepository, FuncionarioRepository funcionarioRepository, TransacaoService transacaoService) {
        this.empresaRepository = empresaRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.transacaoService = transacaoService;
    }

    public List<Empresa> buscarTodos(){ return empresaRepository.findAll(); }

    public Empresa buscarEmpresa(final Long companyId){
        Optional<Empresa> company = empresaRepository.findById(companyId);

        return company.orElseThrow(() -> new ObjectNotFoundException("Empresa não foi localizada."));
    }

    public Empresa inserir(final EmpresaDTO empresaDTO){
        if(findByCnpj(empresaDTO) != null){
            throw new DataIntegrityViolationException("Empresa ja cadastrada.");
        }
        return empresaRepository.save(new Empresa(empresaDTO.getNome(), empresaDTO.getCnpj() ,
                empresaDTO.getAddress(), empresaDTO.getBalance()));
    }

    public Empresa atualizarDados(final Long companyId , @Valid final EmpresaDTO empresaDTO){
        Empresa company = buscarEmpresa(companyId);

        if(findByCnpj(empresaDTO) != null && findByCnpj(empresaDTO).getId() != companyId){
            throw new DataIntegrityViolationException("Empresa ja cadastrada");
        }

        company.setNome(empresaDTO.getNome());
        company.setCnpj(empresaDTO.getCnpj());
        company.setAddress(empresaDTO.getAddress());
        company.setBalance(empresaDTO.getBalance());
        return empresaRepository.save(company);
    }

    public void remover(final Long companyId){
        Empresa company = buscarEmpresa(companyId);

        if(company.getFuncionariosList().size() >0){
            throw new DataIntegrityViolationException("Empresa tem um ou mais funcionario vinculados a ela");
        }

        empresaRepository.delete(company);
    }

    public Empresa consultaSaldo(final Long companyId){
        Optional<Empresa> getCompanyId = empresaRepository.findById(companyId);
        if(getCompanyId.isEmpty()){ return getCompanyId.get(); }
        throw new ObjectNotFoundException("Emrpesa não encontrada!");
    }

    public void tranferir(final TransacaoRequest request){
        Optional<Empresa> empresaOpt = empresaRepository.findById(request.getCompany_id());
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.getByCpf(request.getCpfFuncionario());

        if(!empresaOpt.isPresent()){ throw new ObjectNotFoundException("Empresa não encontrado"); }
        if(!funcionarioOpt.isPresent()){ throw new ObjectNotFoundException("Funcionario não encontrado."); }

        Empresa company = empresaOpt.get();
        Funcionario employee = funcionarioOpt.get();

        if(request.getValor_transacao().compareTo(company.getBalance()) == 1){
            throw new DataIntegrityViolationException("Saldo Insuficiente");
        }

        transacaoService.sacar(company.getId() , request.getValor_transacao());
        transacaoService.depositar(employee.getId() , request.getValor_transacao());

        empresaRepository.save(company);
        funcionarioRepository.save(employee);
    }



    private Empresa findByCnpj(final EmpresaDTO empresaDTO) {
        Empresa company = empresaRepository.findByCnpj(empresaDTO.getCnpj());
        if(company != null){
            return company;
        }
        return null;
    }
}
