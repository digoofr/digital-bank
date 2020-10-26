package br.com.digital.bank.api.dto;

import br.com.digital.bank.api.domain.Pessoa;
import br.com.digital.bank.api.domain.PessoaFisica;
import br.com.digital.bank.api.domain.PessoaJuridica;
import br.com.digital.bank.api.exception.DomainException;
import br.com.digital.bank.api.exception.ResourceNotFoundException;
import br.com.digital.bank.api.utils.CPFCNPJUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

import static br.com.digital.bank.api.utils.VariaveisUtils.MSG_CPF_INVALIDO;
import static br.com.digital.bank.api.utils.VariaveisUtils.MSG_CPF_VAZIO;

@Data @AllArgsConstructor @NoArgsConstructor
@Getter@Setter
public class ContaDto {
    private long id;
    private Long numeroConta;
    private Double saldo;
    private Date dtCadastro;
    private Pessoa pessoa;
    private PessoaFisica pessoaFisica;
    private PessoaJuridica pessoaJuridica;

    public ContaDto(String nome, String cpf, double saldo) {
        getPessoa().setNome(nome);
        getPessoaFisica().setCpf(cpf);
        this.saldo = saldo;
        validarCPF();
    }

    private void validarCPF() {
        if ("".equals(getPessoaFisica().getCpf()) || getPessoaFisica().getCpf() == null) {
            throw new DomainException(MSG_CPF_INVALIDO);
        } else if (CPFCNPJUtils.isCpfValido(getPessoaFisica().getCpf()) == false) {
            throw new DomainException(MSG_CPF_VAZIO);
        }
    }

    public Pessoa getPessoa() {
        if(pessoa == null){
            setPessoa(new Pessoa());
        }
        return pessoa;
    }

    public PessoaFisica getPessoaFisica() {
        if(pessoaFisica == null){
            setPessoaFisica( new PessoaFisica());
        }
        return pessoaFisica;
    }

    public PessoaJuridica getPessoaJuridica() {
        if(pessoaJuridica == null){
            setPessoaJuridica(new PessoaJuridica());
        }
        return pessoaJuridica;
    }
}
