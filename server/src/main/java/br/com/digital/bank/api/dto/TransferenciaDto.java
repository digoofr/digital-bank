package br.com.digital.bank.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data @NoArgsConstructor @AllArgsConstructor
public class TransferenciaDto {
    @NotNull
    private Double valor;
    @NotNull
    private Long contaDoSolicitante;
    @NotNull
    private Long contaDoBeneficiario;
}
