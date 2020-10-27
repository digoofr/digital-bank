package br.com.digital.bank.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "conta")
public class Conta extends PersistentObject implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "numero_conta", length = 150, unique = true)
    private Long numeroConta;

    @Column(name = "saldo")
    private Double saldo;

    @Column(name = "dt_cadastro")
    @Temporal(TemporalType.DATE)
    private Date dtCadastro;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_pessoa")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Pessoa pessoa;

    public Conta(Pessoa pessoa, Double saldo, Long numeroConta) {
        this.pessoa = pessoa;
        this.saldo = saldo;
        this.numeroConta = numeroConta;
    }

}
