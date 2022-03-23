package com.example.demo.resource;

import com.example.demo.dto.EmpresaDTO;
import com.example.demo.entity.Empresa;
import com.example.demo.resource.request.TransacaoRequest;
import com.example.demo.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/company")
@RequiredArgsConstructor
public class EmpresaResource {

    private final EmpresaService empresaService;

    @GetMapping("/all")
    public ResponseEntity<List<EmpresaDTO>> findAll(){
        List<EmpresaDTO> empresaDTOList = empresaService.buscarTodos().stream()
                .map(obj -> new EmpresaDTO(obj)).collect(Collectors.toList());

        return ResponseEntity.ok().body(empresaDTOList);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<EmpresaDTO> findCompany(@PathVariable final Long companyId){
        EmpresaDTO empresaDTO = new EmpresaDTO(empresaService.buscarEmpresa(companyId));

        return ResponseEntity.ok().body(empresaDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<EmpresaDTO> createCompany(@Valid @RequestBody final EmpresaDTO empresaDTO){
        Empresa company = empresaService.inserir(empresaDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{companyId}").buildAndExpand(company.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/update/{companyId}")
    public ResponseEntity<EmpresaDTO> updateCompany(@PathVariable final Long companyId, @Valid @RequestBody EmpresaDTO empresaDTO){
        EmpresaDTO newCompany = new EmpresaDTO(empresaService.atualizarDados(companyId, empresaDTO));
        return ResponseEntity.ok().body(newCompany);
    }

    @DeleteMapping("/delete/{companyId}")
    public ResponseEntity<Void> deleteCompany(@PathVariable final Long companyId){
        empresaService.remover(companyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/saldo/{companyId}")
    public ResponseEntity<BigDecimal> companyBalance(@PathVariable final Long companyId){
        EmpresaDTO empresaDTO = new EmpresaDTO(empresaService.consultaSaldo(companyId));
        return ResponseEntity.ok().body(empresaDTO.getBalance());
    }

    @PostMapping("/transferir")
    public ResponseEntity<?> employeePayment(@Valid @RequestBody final TransacaoRequest request){
        empresaService.tranferir(request);
        return ResponseEntity.ok("Pagamento realizado com sucesso");
    }
}
