package br.com.digital.bank.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor

@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "pessoa")
@NamedQueries({@NamedQuery(name = "Pessoa.findAll", query = "select p from Pessoa p")})
public class Pessoa extends PersistentObject {

    private static final long serialVersionUID = 1747829559963800672L;

    @Column(name = "nome", nullable = false, length = 225)
    private String nome;

    @Column(name = "dt_cadastro")
    @Temporal(TemporalType.DATE)
    private Date dtCadastro;

    /************************************************* GETTERS SETTERS /HASHCODE EQUALS /TOSTRING *************************************************/
    public String getCPFOrCNPJ() {
        if (this instanceof PessoaFisica) {
            return ((PessoaFisica) this).getCpf();
        }
        if (this instanceof PessoaJuridica) {
            return ((PessoaJuridica) this).getCnpj();
        }
        return "";
    }

}