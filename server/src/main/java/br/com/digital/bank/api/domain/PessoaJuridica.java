package br.com.digital.bank.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "pessoa_juridica")
@NamedQueries({@NamedQuery(name = "PessoaJuridica.findAll", query = "select p from PessoaJuridica p")})
@Getter
@Setter
public class PessoaJuridica extends Pessoa {

    private static final long serialVersionUID = -2473929277976338858L;

    @Column(name = "cnpj")
    private String cnpj;

}