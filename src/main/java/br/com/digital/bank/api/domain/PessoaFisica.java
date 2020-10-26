package br.com.digital.bank.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@Table(name = "pessoa_fisica")
@NamedQueries({@NamedQuery(name = "PessoaFisica.findAll", query = "select p from PessoaFisica p")})
@Getter
@Setter
public class PessoaFisica extends Pessoa {

    private static final long serialVersionUID = -2473929277976338832L;

    @Column(name = "cpf", nullable = false, length = 11, unique = true)
    private String cpf;

}