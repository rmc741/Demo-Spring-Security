package com.example.demo.resource.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter @Setter
public class TransacaoRequest {

    @NotNull
    private Long company_id;

    @NotNull
    private String cpfFuncionario;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal valor_transacao;
}
