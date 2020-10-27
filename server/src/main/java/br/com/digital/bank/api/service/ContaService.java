package br.com.digital.bank.api.service;

import br.com.digital.bank.api.domain.Conta;

import java.util.List;


public interface ContaService {
    List<Conta> findAll();
    Conta create(Conta conta);
    Conta findById(Long id);
    Conta findByNumeroConta(Long numeroConta);
    void deleteById(Long id);
    Conta depositar(Conta conta, Double valor);
    Conta sacar(Conta conta, Double valor);
    Long gerarNumeroDaConta();
    boolean verificarLimiteMinimo(Double valor);
    boolean vereficarSaldoNaConta(Conta conta, Double valor);
    boolean verificarLimiteAoCriarConta(Conta conta);
    boolean verificarCpfVazio(String cpf);
    boolean verificarCpfInvalido(String cpf);
    List<Conta> transferir(Conta contaSolicitante, Conta ContaBeneficiario, Double valor);

}
