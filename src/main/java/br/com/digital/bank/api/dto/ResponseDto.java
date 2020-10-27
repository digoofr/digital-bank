package br.com.digital.bank.api.dto;

import br.com.digital.bank.api.domain.Conta;
import br.com.digital.bank.api.exception.Exception;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data @AllArgsConstructor @NoArgsConstructor
public class ResponseDto {

    private String mensagem;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Conta> conta = new ArrayList<>();

    private Exception exception;

    public ResponseDto(String mensagem) {
        this.mensagem = mensagem;
    }

    public ResponseDto(String mensagem, Conta conta) {
        this.mensagem = mensagem;
        this.conta.add(conta);
    }

    public ResponseDto(String mensagem, Exception exception) {
        this.mensagem = mensagem;
        this.exception = exception;
    }

}
