package com.example.demo.resource;

import com.example.demo.dto.FuncionarioDTO;
import com.example.demo.entity.Funcionario;
import com.example.demo.service.FuncionarioService;
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
@RequestMapping("/employee")
@RequiredArgsConstructor
public class FuncionarioResource {

    private FuncionarioService funcionarioService;

    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> findAll(){
        List<FuncionarioDTO> funcionarioDTOList = funcionarioService.buscarTodos().stream()
                .map(obj -> new FuncionarioDTO(obj)).collect(Collectors.toList());

        return ResponseEntity.ok().body(funcionarioDTOList);
    }

    @PostMapping
    public ResponseEntity<FuncionarioDTO> createEmployee(@Valid @RequestBody FuncionarioDTO funcionarioDTO){
        Funcionario employee = funcionarioService.inserir(funcionarioDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{companyId}")
                .buildAndExpand(employee.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<FuncionarioDTO> updateEmployee(@PathVariable final Long employeeId , @Valid @RequestBody FuncionarioDTO funcionarioDTO){
        FuncionarioDTO newEmployee = new FuncionarioDTO(funcionarioService.atualizarDados(employeeId, funcionarioDTO));

        return ResponseEntity.ok().body(newEmployee);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable final Long employeeId){
        funcionarioService.remover(employeeId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/saldo/{employeeId}")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable final Long employeeId){
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO(funcionarioService.consultarSaldo(employeeId));

        return ResponseEntity.ok().body(funcionarioDTO.getBalance());
    }


}
