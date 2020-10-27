package br.com.digital.bank.api.dto;

import br.com.digital.bank.api.domain.Pessoa;
import br.com.digital.bank.api.domain.PessoaFisica;
import br.com.digital.bank.api.domain.PessoaJuridica;
import lombok.*;
import java.util.Date;


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
